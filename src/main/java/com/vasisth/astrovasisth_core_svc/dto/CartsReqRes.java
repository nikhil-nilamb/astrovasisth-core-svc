package com.vasisth.astrovasisth_core_svc.dto;

import lombok.Data;

@Data
public class CartsReqRes {
    private String id;
    private String customerId;
    private String productId;
    private int quantity;
    private String imageUrl;
    private String productName;
    private double price;
    private double totalPrice;
}
