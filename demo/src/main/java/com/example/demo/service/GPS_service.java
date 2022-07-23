package com.example.demo.service;

import com.example.demo.entity.GPS_data;
import com.example.demo.entity.dto.GPS_dto_Get;
import com.example.demo.entity.dto.GPS_dto_Post;
import com.example.demo.entity.dto.GPS_dto_dates;
import com.example.demo.repository.GPS_repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service


public class GPS_service {
@Autowired
private GPS_repository repo;
  public void save_GPS(GPS_dto_Post dto){
      GPS_data data = new GPS_data();
      data.setLatitude(dto.getLatitude());
      data.setLongitude(dto.getLongitude());
      data.setDate(dto.getDate());
      data.setSpeed(dto.getSpeed());
      data.setHeading(dto.getHeading());
      repo.save(data);
  }

    public GPS_data get_byID(int id)
    {
        return repo.findById(id).get();
    }

    public GPS_dto_Get get_last(){
        List<GPS_data> data;
        data=repo.findAll();
        GPS_data temp = null;
        temp=data.get(data.size()-1);
        GPS_dto_Get temp1 = new GPS_dto_Get();
        temp1.setLongitude(temp.getLongitude());
        temp1.setLatitude(temp.getLatitude());
        temp1.setSpeed(temp.getSpeed());
        temp1.setHeading(temp.getHeading());
        return temp1;
    }
  public List<GPS_data> get_All(){
      return repo.findAll();
  }

    public List<GPS_dto_dates> get_date(int day, int month, int year) {
        LocalDateTime myDateObj = LocalDateTime.of(year,month,day,00,00);
        List<GPS_data> data;
        data=repo.findAll();

        List<GPS_dto_dates> data_filtered = new ArrayList<>();

        for(int i=0; i < data.size();i++){
            GPS_data temp = data.get(i);
            LocalDateTime time_temp;
            time_temp = temp.getDate();
            if(time_temp.getDayOfMonth() == myDateObj.getDayOfMonth() && time_temp.getYear() == myDateObj.getYear() && time_temp.getMonth() == myDateObj.getMonth()  ){
                GPS_data temp_Gps = data.get(i);
                GPS_dto_dates temp_dates = new GPS_dto_dates();
                temp_dates.setLatitude(temp_Gps.getLatitude());
                temp_dates.setLongitude(temp_Gps.getLongitude());
                data_filtered.add(temp_dates);
            }
        }
        return data_filtered;
    }
}
