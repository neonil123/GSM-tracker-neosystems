package com.example.demo.controller;

import com.example.demo.entity.GPS_data;
import com.example.demo.entity.dto.GPS_dto_Get;
import com.example.demo.entity.dto.GPS_dto_Post;
import com.example.demo.entity.dto.GPS_dto_dates;
import com.example.demo.service.GPS_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class Controller {
    @Autowired
    private GPS_service service;

    @GetMapping
    public List<GPS_data> get_GPS(){
        List<GPS_data> all = service.get_All();
        return all;
    }
    @GetMapping(path = "{GPSId}")
    public GPS_data get_GPS(@PathVariable("GPSId") int GPSId){
        GPS_data data = service.get_byID(GPSId);
        return data;
    }
    @GetMapping(value = "/last")
    public GPS_dto_Get get_Last_GPS(){
        GPS_dto_Get data = service.get_last();
        return data;
    }
    @GetMapping(value = "/date/{day}/{month}/{year}/{f_h}/{s_h}")
    public List<GPS_dto_dates> get_Date_GPS(@PathVariable("day") int day, @PathVariable("month") int month, @PathVariable("year") int year, @PathVariable("f_h") int f_h, @PathVariable("s_h") int s_h){
        List<GPS_dto_dates> data = service.get_date(day,month,year,f_h,s_h);
        return data;
    }

    @PostMapping
    public String post_GPS(@RequestBody GPS_dto_Post dto){
        dto.setDate(LocalDateTime.now());
        service.save_GPS(dto);
        return("good");
    }

}
