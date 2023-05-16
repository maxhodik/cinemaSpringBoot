package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.dto.HallDto;
import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.repositories.HallRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class HallService {
    private final HallRepository hallRepository;
    private final ModelMapper modelMapper;


    public HallService(HallRepository hallRepository, ModelMapper modelMapper) {
        this.hallRepository = hallRepository;
        this.modelMapper = modelMapper;
    }

    public Hall create(Hall hall) {
      return  updateCapacity(hall);

    }

    private Hall convertToHall(HallDto hallDto) {
        return modelMapper.map(hallDto, Hall.class);
    }

    public Hall findById(int id) {
        return hallRepository.findById(id).orElse(null);
    }

    public Hall updateCapacity(Hall hall) {
        int capacity = hall.getCapacity();
        int numberOfSoldSeats = hall.getNumberOfSoldSeats();
        int numberAvailableSeats = capacity - numberOfSoldSeats;
        return getHall(hall, numberAvailableSeats, capacity, numberOfSoldSeats);

    }

    private Hall getHall(Hall hallToChange, int newAvailableSeats, int capacity, int numberOfSoldSeats) {
        BigDecimal attendance = new BigDecimal((float) numberOfSoldSeats / capacity * 100);
        attendance = attendance.setScale(2, RoundingMode.HALF_UP);
        Hall hall = Hall.builder()
                .id(hallToChange.getId())
                .capacity(capacity)
                .numberAvailableSeats(newAvailableSeats)
                .numberOfSoldSeats(numberOfSoldSeats).attendance(attendance).build();
        hallRepository.saveAndFlush(hall);
        return hall;
    }


}
