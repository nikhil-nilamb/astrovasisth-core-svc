package com.vasisth.astrovasisth_core_svc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String name;
    private Double price;
    private String description;
    private boolean isActive = true;
    private String imageUrls;


}