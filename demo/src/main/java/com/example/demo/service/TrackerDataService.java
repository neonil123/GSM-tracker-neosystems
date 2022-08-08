package com.example.demo.service;

import com.example.demo.entity.TrackerData;
import com.example.demo.entity.dto.TrackerDataDtoGetDates;
import com.example.demo.entity.dto.TrackerDataDtoGet;
import com.example.demo.entity.dto.TrackerDataDtoPost;
import com.example.demo.mapper.Mappers;
import com.example.demo.repository.TrackerDataRepository;
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
public class TrackerDataService {
    @Autowired
    private TrackerDataRepository trackerDataRepository;

    public void saveGPS(TrackerDataDtoPost dto) {
        trackerDataRepository.save(Mappers.dtoPostToData(dto));
    }

    public TrackerData getByID(int id) {
        return trackerDataRepository.findById(id).get();
    }

    public TrackerDataDtoGet getLast(int trackerId) {
        final List<TrackerData> data = trackerDataRepository.findAll();
        List<TrackerData> templist = new ArrayList<>();
       for(TrackerData trackerData: data){
           if(trackerData.getTrackerId() == trackerId){
               templist.add(trackerData);
           }
       }

        final TrackerData temp = templist.get(templist.size() - 1);
        log.info("last by Id:" + trackerId + " " + "is data:" + temp );
        return Mappers.DataToDtoGet(temp);
    }

    public List<TrackerData> getAll() {
        return trackerDataRepository.findAll();
    }

    public List<TrackerDataDtoGetDates> getDate( int trackerId, int day, int month, int year, int first_hour, int second_hour) {
        final LocalDateTime myDateObj = LocalDateTime.of(year, month, day, 00, 00);
        final List<TrackerData> data = trackerDataRepository.findAll();
        List<TrackerDataDtoGetDates> dataFiltered = new ArrayList<>();

        //log.info("day:" + day + " " + "month:" + month + "year:" + year + " " + "f_h:" + first_hour + " " + "s_h" + second_hour);

        for (TrackerData gps_data : data) {
            final LocalDateTime timeTemp = gps_data.getDate();
            if ( gps_data.getTrackerId() == trackerId && timeTemp.getDayOfMonth() == myDateObj.getDayOfMonth() && timeTemp.getYear() == myDateObj.getYear() && timeTemp.getMonth() == myDateObj.getMonth() && timeTemp.getHour() >= first_hour && timeTemp.getHour() <= second_hour) {
                TrackerDataDtoGetDates tempDates = Mappers.DataToDtoDates(gps_data);
                dataFiltered.add(tempDates);
            }
        }
        return dataFiltered;
    }

}
