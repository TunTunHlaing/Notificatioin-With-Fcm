package com.example.notiwithfcm.entity;

import jakarta.persistence.*;

@Entity
public class DeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private String userId;
}
