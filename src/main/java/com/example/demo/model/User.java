package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    public User() {}
}
