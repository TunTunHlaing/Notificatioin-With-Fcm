package com.example.notiwithfcm.service;

import com.example.notiwithfcm.NotiRepo;
import com.example.notiwithfcm.model.NotiMessage;
import com.example.notiwithfcm.model.NotificationParameter;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

@Service
public class FcmService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;
    @Autowired
    private NotiRepo repo;
    public String sendNoti(NotiMessage notiMessage){

        var message = getMessage(notiMessage);

        /*Notification notification = Notification.builder()
                .setTitle(notiMessage.title())
                .setBody(notiMessage.body())
                .setImage(notiMessage.image()).build();

        Message message = Message.builder()
                
                .setToken(notiMessage.token())
                .setNotification(notification)
                .putData("data", notiMessage.data())
                .build();*/
        try{
            firebaseMessaging.sendAsync(message);
            return "Noti Send Successfully";
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error Occour While Sending Noti";
        }

    }

   public void subscribeToTopic(String deviceToken) throws FirebaseMessagingException {
        try{
            FirebaseMessaging.getInstance()
                    .subscribeToTopic(Collections.singletonList(deviceToken), "Ko Tun Noti");
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
   }

    public void UnsubscribeToTopic(String deviceToken) throws FirebaseMessagingException {
        try{
            FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(Collections.singletonList(deviceToken), "Ko Tun Noti");
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
    }

    public void sendNotificationToTopic(NotiMessage notiMessage) throws FirebaseMessagingException {

       var message = getMessage(notiMessage);
        try{
            FirebaseMessaging.getInstance().send(message);
        }
        catch (FirebaseMessagingException e){
            e.printStackTrace();
        }
    }

    private Message getNotiMessage(Long notiId, Optional<String> topic){
       var noti = repo.findById(notiId).orElseThrow();
       Notification notification = Notification.builder()
               .setTitle(noti.getTitle())
               .setBody(noti.getDescription())
               .setImage(noti.getThumbNail())
               .build();

       var topicForNoti = topic.orElse(null);
        return Message.builder()
                .setAndroidConfig(getAndroidConfig(topicForNoti))
                .setApnsConfig(getApnsConfig(topicForNoti))
                .setTopic(topicForNoti)
                .setNotification(notification)
                .build();
    }


    private Message getMessage(NotiMessage notiMessage){
        Notification notification = Notification.builder()
                .setTitle(notiMessage.title())
                .setBody(notiMessage.body())
                .setImage(notiMessage.image()).build();

        return Message.builder()
                .setAndroidConfig(getAndroidConfig(notiMessage.topic()))
                .setApnsConfig(getApnsConfig(notiMessage.topic()))
                .setTopic(notiMessage.topic())
                .setNotification(notification)
                .putData("data", notiMessage.data())
                .build();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue()).setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

}