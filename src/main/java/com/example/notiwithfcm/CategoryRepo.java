package com.example.notiwithfcm;

import com.example.notiwithfcm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
