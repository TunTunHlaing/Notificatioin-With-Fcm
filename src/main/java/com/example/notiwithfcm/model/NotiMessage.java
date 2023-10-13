package com.example.notiwithfcm.model;

public record NotiMessage(
        String token,

        String topic,
        String title,
        String body,
        String image,
        String data
) {
}
