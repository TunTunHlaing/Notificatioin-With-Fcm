package com.example.notiwithfcm.input;

import com.example.notiwithfcm.entity.Content;
import com.example.notiwithfcm.entity.Noti;
import com.example.notiwithfcm.model.ContentType;
import com.example.notiwithfcm.model.NotiButton;
import com.example.notiwithfcm.model.NotificationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class NotificationForm {

    private Long categoryId;
    private String title;
    private MultipartFile thumbNail;
    private String description;
    private LocalDate schedule;
    private String notificationType;
    private Optional<Boolean> button;
    private Optional<String> buttonName;
    private Optional<String> buttonUrl;
    private List<MultipartFile> contentMedia;
    private List<String> bodyTitle;
    private List<String> bodyDescription;

    public Noti entity(NotificationForm form){

        List<Content> contentList = new ArrayList<>();
        var noti = new Noti(title,description, schedule, NotificationType.valueOf(notificationType), contentList);

        if (button.isPresent()){
            noti.setButton(button.get());
            noti.setButtonName(buttonName.get());
            noti.setButtonUrl(buttonUrl.get());
        }

        if (!bodyTitle.isEmpty()){
            for (String bTitle : bodyTitle){
                var content = new Content(ContentType.TITLE, bTitle);
                noti.getContentList().add(content);
            }
        }
        if (!bodyDescription.isEmpty()){
            for (String bBody : bodyDescription){
                var content = new Content(ContentType.DESCRIPTION, bBody);
                noti.getContentList().add(content);
            }
        }
        return noti;
    }
}
