package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.dto.CartsReqRes;

import java.util.List;

public interface CartsService {
    List<CartsReqRes> getAllCarts();
    List<CartsReqRes>  getCartsById(String customerId);
    List<CartsReqRes> createCarts(CartsReqRes carts);
    List<CartsReqRes> updateCarts(String id, CartsReqRes carts);
    void deleteCarts(String productId, String customerId);
}
