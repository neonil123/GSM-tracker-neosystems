package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor

public class TrackerDataDtoPost {
    private double latitude;
    private double longitude;
    private double speed;
    private double heading;
    private int trackerId;
    private LocalDateTime date;
}
