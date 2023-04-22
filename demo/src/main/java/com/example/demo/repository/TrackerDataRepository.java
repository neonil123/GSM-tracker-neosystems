package com.example.demo.repository;

import com.example.demo.entity.TrackerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackerDataRepository extends JpaRepository<TrackerData, Integer> {
    List<TrackerData> findAllByTrackerId(int trackerId);
    void deleteAllByTrackerId(int trackerId);
}
