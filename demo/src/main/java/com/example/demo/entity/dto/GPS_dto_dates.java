package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor

public class GPS_dto_dates {
    private double latitude;
    private double longitude;
}
