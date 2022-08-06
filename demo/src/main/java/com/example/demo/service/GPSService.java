package com.example.demo.service;

import com.example.demo.entity.TrackerData;
import com.example.demo.entity.dto.GPSDtoDates;
import com.example.demo.entity.dto.GPSDtoGet;
import com.example.demo.entity.dto.GPSDtoPost;
import com.example.demo.mapper.Mappers;
import com.example.demo.repository.GPSRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service

@Slf4j
public class GPSService {
    @Autowired
    private GPSRepository repo;

    public void saveGPS(GPSDtoPost dto) {
        repo.save(Mappers.dtoPostToData(dto));
    }

    public TrackerData getByID(int id) {
        return repo.findById(id).get();
    }

    public GPSDtoGet getLast() {
        final List<TrackerData> data = repo.findAll();
        final TrackerData temp = data.get(data.size() - 1);
        return Mappers.DataToDtoGet(temp);
    }

    public List<TrackerData> getAll() {
        return repo.findAll();
    }

    public List<GPSDtoDates> getDate(int day, int month, int year, int first_hour, int second_hour) {
        final LocalDateTime myDateObj = LocalDateTime.of(year, month, day, 00, 00);
        final List<TrackerData> data = repo.findAll();
        List<GPSDtoDates> dataFiltered = new ArrayList<>();

        //log.info("day:" + day + " " + "month:" + month + "year:" + year + " " + "f_h:" + first_hour + " " + "s_h" + second_hour);

        for (TrackerData gps_data : data) {
            final LocalDateTime timeTemp = gps_data.getDate();
            if (timeTemp.getDayOfMonth() == myDateObj.getDayOfMonth() && timeTemp.getYear() == myDateObj.getYear() && timeTemp.getMonth() == myDateObj.getMonth() && timeTemp.getHour() >= first_hour && timeTemp.getHour() <= second_hour) {
                GPSDtoDates tempDates = Mappers.DataToDtoDates(gps_data);
                dataFiltered.add(tempDates);
            }
        }
        return dataFiltered;
    }

}
