package com.example.demo.service;

import com.example.demo.entity.Tracker;
import com.example.demo.entity.TrackerData;
import com.example.demo.entity.dto.TrackerDataDtoGetDates;
import com.example.demo.entity.dto.TrackerDto;
import com.example.demo.entity.dto.TrackerDtoGetLastTrackerData;
import com.example.demo.mapper.Mappers;
import com.example.demo.repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackerService {
    @Autowired
    private TrackerRepository trackerRepository;

    public List<Tracker> getAllTrackers() {
        return trackerRepository.findAll();
    }

    public Tracker addCar(TrackerDto trackerDto) {
        return trackerRepository.save(Mappers.carDtoToCar(trackerDto));
    }

    public List<TrackerDto> getUserTrackers(int userId) {
        List<Tracker> trackers = trackerRepository.findAll();
        List<TrackerDto> trackerDtos = new ArrayList<>();

        for(Tracker tracker: trackers){
            System.out.println(userId + " " + tracker.getUserId() );
            if(tracker.getUserId() == userId){
                trackerDtos.add(Mappers.trackerToTrackerDto(tracker));
            }
        }
        return trackerDtos;
    }

    public List<TrackerDtoGetLastTrackerData> getTrackersLastData(int userId) {
        List<Tracker> trackers = trackerRepository.findAll();
        List<TrackerDtoGetLastTrackerData> tempTrackerData = new ArrayList<>();

        for(Tracker tracker: trackers){
            if(tracker.getUserId() == userId){
                final TrackerData trackerData = tracker.getTrackerData().get(tracker.getTrackerData().size()-1);
                tempTrackerData.add(Mappers.TrackerToTrackerDtoGetLastTrackerData(trackerData));
            }
        }
      return tempTrackerData;
    }
}
