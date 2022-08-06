package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Setter

@Entity
public class Car {
    @Id
    @GeneratedValue
    @Column(name = "carid")
    private int id;
    private int userId;
    private int hardwareId;
    private String name;

    @OneToMany
    @JoinColumn(name="carId")
    private List<TrackerData> trackerData;
}
