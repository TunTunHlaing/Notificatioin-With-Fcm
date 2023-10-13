package com.example.notiwithfcm.service;

import com.example.notiwithfcm.CategoryRepo;
import com.example.notiwithfcm.NotiRepo;
import com.example.notiwithfcm.entity.Content;
import com.example.notiwithfcm.input.NotificationForm;
import com.example.notiwithfcm.model.ContentType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class NotificationService {

    @Autowired
    private NotiRepo repo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Value("${file.path}")
    private String imageStorage;
    private Path storagePath;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    @PostConstruct
    private void postConstruct() {
        storagePath = Path.of(imageStorage);
        if(!Files.exists(storagePath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createDirectories(storagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String save(NotificationForm form){
        var noti = form.entity(form);
        var cat = categoryRepo.findById(form.getCategoryId()).orElseThrow();
        var media = saveImage(form.getThumbNail());
        if (!form.getContentMedia().isEmpty()){
            for (MultipartFile img : form.getContentMedia()){
                var bodyImage = saveImage(img);
                noti.getContentList().add(new Content(ContentType.IMAGE, bodyImage));
            }
        }
        noti.setCategory(cat);
        repo.save(noti);
        return "Successfully Update";
    }


  private String saveImage(MultipartFile file){
      var path = storagePath.resolve(file.getOriginalFilename());

      try {
          Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception e) {
          e.printStackTrace();
      }
      return file.getOriginalFilename();
  }
}
