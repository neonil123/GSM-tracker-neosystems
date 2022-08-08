package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Setter

@Entity
public class Tracker {
    @Id
    @GeneratedValue
    @Column(name = "carid")
    private int id;
    private int userId;
    private int hardwareId;
    private String name;

    @OneToMany
    @JoinColumn(name="trackerId")
    private List<TrackerData> trackerData;
}
