package com.example.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@RequiredArgsConstructor
//@NoArgsConstructor
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
}
