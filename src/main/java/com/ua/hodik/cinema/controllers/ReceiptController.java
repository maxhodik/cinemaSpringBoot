package com.ua.hodik.cinema.controllers;

import com.ua.hodik.cinema.dto.ReceiptDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.dto.UserDto;
import com.ua.hodik.cinema.exceptions.EntityNotFoundException;
import com.ua.hodik.cinema.exceptions.NotEnoughAvailableSeats;
import com.ua.hodik.cinema.exceptions.ReceiptNotCreatedException;
import com.ua.hodik.cinema.model.Receipt;
import com.ua.hodik.cinema.model.User;
import com.ua.hodik.cinema.services.ReceiptService;
import com.ua.hodik.cinema.services.ScheduleService;
import com.ua.hodik.cinema.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/receipt")
public class ReceiptController {
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ReceiptService receiptService;
    private final Validator receiptValidator;

    public ReceiptController(ScheduleService scheduleService, UserService userService, ModelMapper modelMapper,
                             ReceiptService receiptService, @Qualifier("receiptValidator") Validator receiptValidator) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.receiptService = receiptService;
        this.receiptValidator = receiptValidator;
    }


    @GetMapping("/{id}")
    public String receipt(@PathVariable("id") int id, Model model, HttpSession session, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByName(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new);
        session.setAttribute("userDto", convertToUserDto(user));
        model.addAttribute("sessionAdminDto", scheduleService.findById(id));
        model.addAttribute("receiptDto", new ReceiptDto());
        return "receipt";
    }

    @PostMapping("/{id}")
    public String createReceipt(@ModelAttribute @Valid ReceiptDto receiptDto, BindingResult bindingResult, Model model) {
        int id= receiptDto.getSessionId();
        SessionAdminDto sessionDto = scheduleService.findById(id);
                if (bindingResult.hasErrors()) {
            model.addAttribute("sessionAdminDto", sessionDto);
            return "receipt";
        }
            receiptValidator.validate(receiptDto, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("sessionAdminDto", sessionDto);
                return "receipt";
            }
            Receipt receipt;
            try {
                receipt = receiptService.submitReceipt(receiptDto);
            } catch (NotEnoughAvailableSeats e) {
                bindingResult.addError(new ObjectError("numberOfSeats", "Not enough available seats"));
                model.addAttribute("sessionAdminDto", sessionDto);
                return "receipt";
            } catch (ReceiptNotCreatedException e) {
                throw new RuntimeException(e);
            }
            return "redirect:/ticket/" + receipt.getId();

        }

        private UserDto convertToUserDto (User user){
            return modelMapper.map(user, UserDto.class);
        }

    }
