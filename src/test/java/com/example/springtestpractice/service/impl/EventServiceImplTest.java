package com.example.springtestpractice.service.impl;

import com.example.springtestpractice.model.Event;
import com.example.springtestpractice.repository.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void getAll() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event());
        eventList.add(new Event());

        doReturn(eventList).when(eventRepository).findAll();

        List<Event> result = eventService.getAll();

        verify(eventRepository, atLeastOnce()).findAll();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(eventList, result);
    }

    @Test
    void getById() {
        Event event = new Event();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event result = eventService.getById(1L);

        verify(eventRepository, atLeastOnce()).findById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(event, result);
    }

    @Test
    void findByFile_Id() {
        Event event = new Event();

        when(eventRepository.findByFile_Id(1L)).thenReturn(event);

        Event result = eventService.findByFile_Id(1L);

        verify(eventRepository, atLeastOnce()).findByFile_Id(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(event, result);
    }

    @Test
    void create() {
        Event event = new Event();

        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.create(event);

        verify(eventRepository, atLeastOnce()).save(event);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(event, result);
    }

    @Test
    void update() {
        Event event = new Event();

        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.update(event);

        verify(eventRepository, atLeastOnce()).save(event);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(event, result);
    }

    @Test
    void delete() {
        Event event = new Event();

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        eventService.delete(1L);

        verify(eventRepository).deleteById(1L);
    }
}