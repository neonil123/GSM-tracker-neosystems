package com.example.demo.controller;

import com.example.demo.entity.TrackerData;
import com.example.demo.entity.dto.TrackerDataDtoGet;
import com.example.demo.entity.dto.TrackerDataDtoPost;
import com.example.demo.entity.dto.TrackerDataDtoGetDates;
import com.example.demo.service.TrackerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class TrackerDataController {
    @Autowired
    private TrackerDataService service;

    @GetMapping
    public List<TrackerData> getGPS() {
        return service.getAll();
    }

    @GetMapping(path = "{GPSId}")
    public TrackerData getGPS(@PathVariable("GPSId") int GPSId) {
        return service.getByID(GPSId);
    }

    @GetMapping(value = "/last/{trackerId}")
    public TrackerDataDtoGet getLastGPS(@PathVariable("trackerId") int trackerId) {
        return service.getLast(trackerId);
    }

    @GetMapping(value = "/date/{trackerId}/{day}/{month}/{year}/{f_h}/{s_h}")
    public List<TrackerDataDtoGetDates> getDateGPS(@PathVariable("trackerId") int trackerId, @PathVariable("day") int day, @PathVariable("month") int month, @PathVariable("year") int year, @PathVariable("f_h") int f_h, @PathVariable("s_h") int s_h) {
        return service.getDate(trackerId,day, month, year, f_h, s_h);
    }

    @PostMapping
    public String postGPS(@RequestBody TrackerDataDtoPost dto) {
        dto.setDate(LocalDateTime.now());
        service.saveGPS(dto);
        return ("good");
    }
}
