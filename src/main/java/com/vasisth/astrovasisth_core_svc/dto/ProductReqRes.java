package com.vasisth.astrovasisth_core_svc.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductReqRes {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String imageUrls;
}
