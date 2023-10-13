package com.example.notiwithfcm.api;

import com.example.notiwithfcm.input.NotificationForm;
import com.example.notiwithfcm.service.FcmService;
import com.example.notiwithfcm.model.NotiMessage;
import com.example.notiwithfcm.service.NotificationService;
import com.google.firebase.messaging.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotiController {

    //ebb867f0-edc0-4d7e-8eb2-13bd57d9fe08

    @Autowired
    private FcmService service;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FirebaseMessaging messaging;

    @PostMapping
    public String saveNotification(NotificationForm form){
        return notificationService.save(form);
    }

    @PostMapping
    public String sendNoti(@RequestBody NotiMessage message) throws FirebaseMessagingException {
        return service.sendNoti(message);
    }
}
