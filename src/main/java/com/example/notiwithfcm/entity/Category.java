package com.example.notiwithfcm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany
    private List<NotificationForUser> notificationForUserList;

    public void addNotificationForUser(NotificationForUser notification){
        notificationForUserList.add(notification);
        notification.setCategory(this);
    }

}
