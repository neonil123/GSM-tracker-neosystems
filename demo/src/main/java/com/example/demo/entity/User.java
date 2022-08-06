package com.example.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.util.List;

@Getter
@RequiredArgsConstructor
//@NoArgsConstructor
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
    private List<Car> cars;
}
