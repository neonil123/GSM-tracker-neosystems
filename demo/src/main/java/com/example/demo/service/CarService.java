package com.example.demo.service;

import com.example.demo.entity.Car;
import com.example.demo.entity.dto.CarDto;
import com.example.demo.mapper.Mappers;
import com.example.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car addCar(CarDto carDto) {
        return carRepository.save(Mappers.carDtoToCar(carDto));
    }
}
