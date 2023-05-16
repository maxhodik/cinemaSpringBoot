package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.repositories.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ScheduleService {
    public final ScheduleRepository scheduleRepository;
    public final HallService hallService;
    public final MovieService movieService;
    public final ModelMapper modelMapper;


    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, HallService hallService, MovieService movieService, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.hallService = hallService;
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    public Page<SessionAdminDto> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(this::convertToSessionAdminDto);
    }

    public SessionAdminDto findById(int id) {
        Session session = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Session not found"));
        return convertToSessionAdminDto(session);

    }

    @Transactional
    public void update(SessionAdminDto sessionAdminDto) {
        Session session = convertToSession(sessionAdminDto);
        int id = sessionAdminDto.getHallId();
        Hall hall = hallService.findById(id);
        hall.setCapacity(sessionAdminDto.getCapacity());
        hall = hallService.updateCapacity(hall);
        Movie movie = convertToMovie(movieService.findByName(sessionAdminDto.getMovieName()));
        session.setMovie(movie);
        session.setHall(hall);
        scheduleRepository.save(session);
    }


    public void delete(int id) {
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public void create(SessionAdminDto sessionAdminDto) {
        Hall hall = new Hall();
        Session session = convertToSession(sessionAdminDto);
        hall.setCapacity(sessionAdminDto.getCapacity());
        hall = hallService.create(hall);
        Movie movie = convertToMovie(movieService.findByName(sessionAdminDto.getMovieName()));
        session.setMovie(movie);
        session.setHall(hall);
        scheduleRepository.save(session);
    }

    private SessionAdminDto convertToSessionAdminDto(Session session) {
        SessionAdminDto sessionAdminDto = modelMapper.map(session, SessionAdminDto.class);
        Hall hall = session.getHall();
        sessionAdminDto.setCapacity(hall.getCapacity());
        sessionAdminDto.setNumberOfAvailableSeats(hall.getNumberAvailableSeats());
        sessionAdminDto.setNumberOfSoldSeats(hall.getNumberOfSoldSeats());
        sessionAdminDto.setAttendance(hall.getAttendance());
        sessionAdminDto.setHallId(hall.getId());
        return sessionAdminDto;
    }

    private Session convertToSession(SessionAdminDto sessionAdminDto) {
        return modelMapper.map(sessionAdminDto, Session.class);

    }

    private Movie convertToMovie(MovieDto movieDto) {
        return modelMapper.map(movieDto, Movie.class);
    }
}
