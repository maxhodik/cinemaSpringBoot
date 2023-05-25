package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.dto.ReceiptDto;
import com.ua.hodik.cinema.exceptions.EntityNotFoundException;
import com.ua.hodik.cinema.exceptions.NotEnoughAvailableSeats;
import com.ua.hodik.cinema.exceptions.ReceiptNotCreatedException;
import com.ua.hodik.cinema.model.*;
import com.ua.hodik.cinema.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ReceiptService {
    private final ScheduleService scheduleService;
    private final HallService hallService;
    private final UserService userService;
    private final ReceiptRepository receiptRepository;


    public ReceiptService(ScheduleService scheduleService, HallService hallService, UserService userService, ReceiptRepository receiptRepository) {
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.userService = userService;
        this.receiptRepository = receiptRepository;
    }

    @Transactional
    public Receipt submitReceipt(ReceiptDto receiptDto) throws NotEnoughAvailableSeats, ReceiptNotCreatedException {
        int sessionId = receiptDto.getSessionId();
        Session session = scheduleService.findSessionById(sessionId);
        Hall hall = session.getHall();
        int seats = getSeats(receiptDto, hall);
        User user = userService.findByName(receiptDto.getUserName()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Receipt receipt = Receipt.builder()
                .state(State.getByNameIgnoringCase("NEW"))
                .session(session)
                .numberOfSeats(seats)
                .user(user)
                .price(100).build();
        receiptRepository.saveAndFlush(receipt);
        hallService.updateSoldSeats(hall, seats);
        return receipt;
    }

    private static int getSeats(ReceiptDto receiptDto, Hall hall) throws NotEnoughAvailableSeats {
        int numberAvailableSeats = hall.getNumberAvailableSeats();
        int seats = receiptDto.getNumberOfSeats();
        int newNumberAvailableSeats = numberAvailableSeats - seats;
        if (newNumberAvailableSeats < 0) {
            throw new NotEnoughAvailableSeats("Not enough available seats");
        }
        return seats;
    }

    public Optional<Receipt> findById(int id) {
       return receiptRepository.findById(id);
    }
}
