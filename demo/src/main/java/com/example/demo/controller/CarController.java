package com.example.demo.controller;

import com.example.demo.entity.Car;
import com.example.demo.entity.dto.CarDto;
import com.example.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/car")

public class CarController {
    @Autowired
    private CarService carService;


    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    public Car addCar(@RequestBody CarDto carDto) {
        return carService.addCar(carDto);
    }
}
