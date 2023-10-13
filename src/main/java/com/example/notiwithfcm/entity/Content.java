package com.example.notiwithfcm.entity;

import com.example.notiwithfcm.model.ContentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private ContentType contentType;
    private String value;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Noti notification;

    public Content(ContentType contentType, String value) {
        this.contentType = contentType;
        this.value = value;
    }
}
