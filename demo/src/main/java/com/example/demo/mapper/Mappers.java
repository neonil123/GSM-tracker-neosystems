package com.example.demo.mapper;

import com.example.demo.entity.Tracker;
import com.example.demo.entity.TrackerData;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.*;

public class Mappers {

   static public TrackerData dtoPostToData(TrackerDataDtoPost dto) {
        TrackerData data = new TrackerData();
        data.setLatitude(dto.getLatitude());
        data.setLongitude(dto.getLongitude());
        data.setDate(dto.getDate());
        data.setSpeed(dto.getSpeed());
        data.setHeading(dto.getHeading());
        data.setTrackerId(dto.getTrackerId());
        return data;
    }

   static public TrackerDataDtoGetDates DataToDtoDates(TrackerData gps_data) {
        TrackerDataDtoGetDates temp_dates = new TrackerDataDtoGetDates();
        temp_dates.setLatitude(gps_data.getLatitude());
        temp_dates.setLongitude(gps_data.getLongitude());
        return temp_dates;
    }


    static public TrackerDataDtoGet DataToDtoGet(TrackerData temp) {
        TrackerDataDtoGet temp1 = new TrackerDataDtoGet();
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

    public static Tracker carDtoToCar(TrackerDto trackerDto) {
       Tracker tracker = new Tracker();
       tracker.setId(trackerDto.getId());
       tracker.setName(trackerDto.getName());
       tracker.setUserId(trackerDto.getUserId());
       return tracker;
    }

    public static TrackerDto trackerToTrackerDto(Tracker tracker) {
       TrackerDto trackerDto = new TrackerDto();
       trackerDto.setName(tracker.getName());
       trackerDto.setId(tracker.getId());
       return trackerDto;
    }

    public static TrackerDtoGetLastTrackerData TrackerToTrackerDtoGetLastTrackerData(TrackerData trackerData) {
       TrackerDtoGetLastTrackerData trackerDtoGetLastTrackerData = new TrackerDtoGetLastTrackerData();
       trackerDtoGetLastTrackerData.setTrackerId(trackerData.getTrackerId());
       trackerDtoGetLastTrackerData.setHeading(trackerData.getHeading());
       trackerDtoGetLastTrackerData.setSpeed(trackerData.getSpeed());
       trackerDtoGetLastTrackerData.setLongitude(trackerData.getLongitude());
       trackerDtoGetLastTrackerData.setLatitude(trackerData.getLatitude());
       return trackerDtoGetLastTrackerData;
    }
}
