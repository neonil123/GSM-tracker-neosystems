package com.example.demo.repository;

import com.example.demo.entity.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackerRepository extends JpaRepository<Tracker,Integer> {

}
