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
    private int id;
    private int userId;
    private String name;

    @OneToMany()
    ///@OneToMany(mappedBy = "trackerId", cascade = CascadeType.REMOVE)
    @JoinColumn(name="trackerId")
    private List<TrackerData> trackerData;
}
