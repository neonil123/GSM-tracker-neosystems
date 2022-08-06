package com.example.demo.controller;

import com.example.demo.entity.TrackerData;
import com.example.demo.entity.dto.GPSDtoGet;
import com.example.demo.entity.dto.GPSDtoPost;
import com.example.demo.entity.dto.GPSDtoDates;
import com.example.demo.service.GPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class DataController {
    @Autowired
    private GPSService service;

    @GetMapping
    public List<TrackerData> get_GPS(){
        List<TrackerData> all = service.getAll();
        return all;
    }
    @GetMapping(path = "{GPSId}")
    public TrackerData get_GPS(@PathVariable("GPSId") int GPSId){
        TrackerData data = service.getByID(GPSId);
        return data;
    }
    @GetMapping(value = "/last")
    public GPSDtoGet get_Last_GPS(){
        GPSDtoGet data = service.getLast();
        return data;
    }
    @GetMapping(value = "/date/{day}/{month}/{year}/{f_h}/{s_h}")
    public List<GPSDtoDates> get_Date_GPS(@PathVariable("day") int day, @PathVariable("month") int month, @PathVariable("year") int year, @PathVariable("f_h") int f_h, @PathVariable("s_h") int s_h){
        List<GPSDtoDates> data = service.getDate(day,month,year,f_h,s_h);
        return data;
    }

    @PostMapping
    public String post_GPS(@RequestBody GPSDtoPost dto){
        dto.setDate(LocalDateTime.now());
        service.saveGPS(dto);
        return("good");
    }

}
