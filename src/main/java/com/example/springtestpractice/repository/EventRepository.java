package com.example.springtestpractice.repository;

import com.example.springtestpractice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByFile_Id(Long id);
}
