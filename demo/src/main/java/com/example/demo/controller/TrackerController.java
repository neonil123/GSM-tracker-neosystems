package com.example.demo.controller;

import com.example.demo.entity.Tracker;
import com.example.demo.entity.TrackerData;
import com.example.demo.entity.dto.TrackerDto;
import com.example.demo.entity.dto.TrackerDtoGetLastTrackerData;
import com.example.demo.service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/tracker")

public class TrackerController {
    @Autowired
    private TrackerService trackerService;


    @GetMapping
    public List<Tracker> getAllCars() {
        return trackerService.getAllTrackers();
    }

    @GetMapping(path = "/{userId}")
    public List<TrackerDto> getUserTrackers(@PathVariable("userId") int userId){
        return trackerService.getUserTrackers(userId);
    }

    @GetMapping(path = "/lasts/{userId}")
    public List<TrackerDtoGetLastTrackerData> getTrackersLastData(@PathVariable("userId") int userId){
        return trackerService.getTrackersLastData(userId);
    }

    @PostMapping
    public Tracker addCar(@RequestBody TrackerDto trackerDto) {
        return trackerService.addCar(trackerDto);
    }
}
