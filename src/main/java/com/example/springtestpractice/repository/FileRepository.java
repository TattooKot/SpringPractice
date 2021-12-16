package com.example.springtestpractice.repository;

import com.example.springtestpractice.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<File, Long> {
    File findByLocation(String location);
}