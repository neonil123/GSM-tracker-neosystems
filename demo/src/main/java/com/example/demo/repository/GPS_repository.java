package com.example.demo.repository;

import com.example.demo.entity.GPS_data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GPS_repository extends JpaRepository<GPS_data, Integer> {
    List<GPS_data> findAllByDate(LocalDateTime publicationDate);
}
