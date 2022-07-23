package com.example.demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@RequiredArgsConstructor
//@NoArgsConstructor
@Setter



@Entity
public class GPS_data {
    @Id
    @GeneratedValue
    private int id;
    private double latitude;
    private double longitude;
    private double speed;
    private double heading;
    private LocalDateTime date;
}
