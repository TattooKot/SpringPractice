package com.example.springtestpractice.service.impl;

import com.example.springtestpractice.model.Event;
import com.example.springtestpractice.repository.EventRepository;
import com.example.springtestpractice.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = eventRepository.findAll();

        log.info("IN EventServiceImpl getAll - {} events found", events.size());

        return events;
    }

    @Override
    public Event getById(Long id) {
        Event event = eventRepository.findById(id).orElse(null);

        if(event == null){
            log.warn("IN EventServiceImpl getById - event with id {} not found", id);
            return null;
        }

        log.info("IN EventServiceImpl getById - event with id '{}' founded", id);

        return event;
    }

    @Override
    public Event findByFile_Id(Long id){
        Event result = eventRepository.findByFile_Id(id);

        if(result == null){
            log.warn("In EventServiceImpl findByFile_Id - event with id {} does not exist", id);
            throw new IllegalArgumentException();
        }

        log.info("In EventServiceImpl findByFile_Id - event with id {} founded", id);

        return result;
    }

    @Override
    public Event create(Event event) {
        event.setCreated(new Date());

        Event created = eventRepository.save(event);

        log.info("IN EventServiceImpl create - new event successfully created");

        return created;
    }

    @Override
    public Event update(Event event) {
        Event updated = eventRepository.save(event);

        log.info("IN EventServiceImpl update - event successfully updated");

        return updated;
    }

    @Override
    public void delete(Long id) {
        Event event = getById(id);

        if(event == null){
            throw new IllegalArgumentException();
        }

        eventRepository.deleteById(id);

        log.info("IN EventServiceImpl delete - event with id {} successfully deleted", id);
    }
}
