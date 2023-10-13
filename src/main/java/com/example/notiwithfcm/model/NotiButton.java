package com.example.notiwithfcm.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class NotiButton {
    private boolean button;
    private String buttonName;
    private String buttonUrl;
}
