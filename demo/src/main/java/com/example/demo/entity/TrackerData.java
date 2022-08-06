package com.example.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Setter

@Entity
public class TrackerData {
    @Id
    @GeneratedValue
    private int id;
    private double latitude;
    private double longitude;
    private double speed;
    private double heading;
    private int carId;
    private LocalDateTime date;
}
