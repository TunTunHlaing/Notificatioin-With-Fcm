package com.example.notiwithfcm;

import com.example.notiwithfcm.entity.Noti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiRepo extends JpaRepository<Noti, Long> {
}
