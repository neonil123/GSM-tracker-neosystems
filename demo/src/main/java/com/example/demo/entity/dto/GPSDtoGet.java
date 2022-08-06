package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class GPSDtoGet {
    private double latitude;
    private double longitude;
    private double speed;
    private double heading;
}
