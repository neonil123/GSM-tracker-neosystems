package com.example.demo.repository;

import com.example.demo.entity.TrackerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GPSRepository extends JpaRepository<TrackerData, Integer> {
    List<TrackerData> findAllByDate(LocalDateTime publicationDate);
}
