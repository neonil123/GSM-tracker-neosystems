package com.example.demo.entity.dto;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor

public class TrackerDtoGetLastTrackerData {
    private double latitude;
    private double longitude;
    private double speed;
    private double heading;
    private int trackerId;
}
