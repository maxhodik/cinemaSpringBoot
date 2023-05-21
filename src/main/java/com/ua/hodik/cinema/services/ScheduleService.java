package com.ua.hodik.cinema.services;

import com.ua.hodik.cinema.dao.SessionSpecification;
import com.ua.hodik.cinema.dto.FilterDto;
import com.ua.hodik.cinema.dto.FilterFormDto;
import com.ua.hodik.cinema.dto.MovieDto;
import com.ua.hodik.cinema.dto.SessionAdminDto;
import com.ua.hodik.cinema.model.Hall;
import com.ua.hodik.cinema.model.Movie;
import com.ua.hodik.cinema.model.Session;
import com.ua.hodik.cinema.model.Status;
import com.ua.hodik.cinema.repositories.ScheduleRepository;
import com.ua.hodik.cinema.util.ConvertToFilterDto;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final HallService hallService;
    private final MovieService movieService;
    private final ModelMapper modelMapper;
    private final ConvertToFilterDto convertToFilterDto;

    private final SessionSpecification sessionSpecification;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, HallService hallService, MovieService movieService, ModelMapper modelMapper, ConvertToFilterDto convertToFilterDto, SessionSpecification sessionSpecification) {
        this.scheduleRepository = scheduleRepository;
        this.hallService = hallService;
        this.movieService = movieService;
        this.modelMapper = modelMapper;
        this.convertToFilterDto = convertToFilterDto;

        this.sessionSpecification = sessionSpecification;
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
        Session session = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Session not found"));
        session.setStatus(Status.CANCELED);
        scheduleRepository.save(session);
    }

    @Transactional
    public void create(SessionAdminDto sessionAdminDto) {
        Hall hall = new Hall();
        Session session = convertToSession(sessionAdminDto);
        hall.setCapacity(sessionAdminDto.getCapacity());
        hall = hallService.create(hall);
        Movie movie = convertToMovie(movieService.findByName(sessionAdminDto.getMovieName()));
        session.setMovie(movie);
        session.setStatus(Status.ACTIVE);
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

    public Page<SessionAdminDto> findAllWithFilters(FilterFormDto filterFormDto, Pageable pageable) {
        Map<String, FilterDto<?>> filters = convertToFilterDto.convert(filterFormDto);
        Specification<Session> specification = sessionSpecification.getSessions(filters);
        return scheduleRepository.findAll(specification, pageable)
                .map(this::convertToSessionAdminDto);
    }

}
