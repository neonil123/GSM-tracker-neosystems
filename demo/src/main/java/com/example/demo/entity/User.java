package com.example.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Setter

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String username;
    private String password;

    @OneToMany()
    @JoinColumn(name="userId")
    private List<Tracker> trackers;
}
