package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.TicketDto;
import com.ua.hodik.cinema.model.Receipt;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.services.ReceiptService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    private final ReceiptService receiptService;

    public TicketController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{id}")
    public String ticket(@PathVariable("id") int id, Model model) {
        Receipt receipt = receiptService.findById(id).orElseThrow(() -> new EntityNotFoundException("Receipt not found"));
        Session session = receipt.getSession();
        User user = receipt.getUser();
        TicketDto ticketDto = TicketDto.builder()
                .time(session.getTime())
                .seats(receipt.getNumberOfSeats())
                .date(session.getDate())
                .price(receipt.getPrice())
                .userName(user.getName())
                .movieName(session.getMovie().getName())
                .build();
        model.addAttribute("ticketDto", ticketDto);
        return "ticket";
    }


}
