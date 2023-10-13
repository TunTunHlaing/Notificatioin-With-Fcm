package com.example.notiwithfcm.entity;

import com.example.notiwithfcm.model.NotiButton;
import com.example.notiwithfcm.model.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_noti", allocationSize = 1)
public class Noti {

    @Id
    @GeneratedValue(generator = "seq_noti")
    private Long id;
    private String title;
    private String description;
    private LocalDate schedule;
    @Enumerated
    private NotificationType notificationType;
    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Content> contentList = new ArrayList<>();
    private String thumbNail;
    private boolean button;
    private String buttonName;
    private String buttonUrl;
    @ManyToOne
    private Category category;

    public Noti(String title, String description, LocalDate schedule, NotificationType notificationType, List<Content> contentList) {
        this.title = title;
        this.description = description;
        this.schedule = schedule;
        this.notificationType = notificationType;
        this.contentList = contentList;
    }

    private void addContentList(Content content){
        contentList.add(content);
        content.setNotification(this);
    }
}
