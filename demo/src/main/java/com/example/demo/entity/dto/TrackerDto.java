package com.example.demo.entity.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TrackerDto {
    private int id;
    private int hardwareId;
    private String name;
    private int userId;
}
