package com.example.demo.mapper;

import com.example.demo.entity.Car;
import com.example.demo.entity.TrackerData;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.*;

public class Mappers {

   static public TrackerData dtoPostToData(GPSDtoPost dto) {
        TrackerData data = new TrackerData();
        data.setLatitude(dto.getLatitude());
        data.setLongitude(dto.getLongitude());
        data.setDate(dto.getDate());
        data.setSpeed(dto.getSpeed());
        data.setHeading(dto.getHeading());
        return data;
    }

   static public GPSDtoDates DataToDtoDates(TrackerData gps_data) {
        GPSDtoDates temp_dates = new GPSDtoDates();
        temp_dates.setLatitude(gps_data.getLatitude());
        temp_dates.setLongitude(gps_data.getLongitude());
        return temp_dates;
    }


    static public GPSDtoGet DataToDtoGet(TrackerData temp) {
        GPSDtoGet temp1 = new GPSDtoGet();
        temp1.setLongitude(temp.getLongitude());
        temp1.setLatitude(temp.getLatitude());
        temp1.setSpeed(temp.getSpeed());
        temp1.setHeading(temp.getHeading());
        return temp1;
    }

    static public User userDtoToUser(UserDto userDto) {
       User user = new User();
       user.setPassword(userDto.getPassword());
       user.setUsername(userDto.getUsername());
       return user;
    }

    public static Car carDtoToCar(CarDto carDto) {
       Car car = new Car();
       car.setName(carDto.getName());
       car.setHardwareId(carDto.getHardwareId());
       car.setUserId(carDto.getUserId());
       return car;
    }
}
