package com.example.springtestpractice.service;

import com.example.springtestpractice.model.Event;

import java.util.List;

public interface EventService {

    List<Event> getAll();

    Event getById(Long id);

    Event create(Event event);

    Event update(Event event);

    Event findByFile_Id(Long id);

    void delete(Long id);

}
