package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.dto.CartsReqRes;
import com.vasisth.astrovasisth_core_svc.repo.CartsRepository;
import com.vasisth.astrovasisth_core_svc.service.CartsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartsServiceImpl implements CartsService {

    private final CartsRepository cartsRepository;


    @Override
    public List<CartsReqRes> getAllCarts() {
        return List.of();
    }

    @Override
    public List<CartsReqRes> getCartsById(String customerId) {
        return List.of();
    }

    @Override
    public List<CartsReqRes> createCarts(CartsReqRes carts) {
        return List.of();
    }

    @Override
    public List<CartsReqRes> updateCarts(String id, CartsReqRes carts) {
        return List.of();
    }

    @Override
    public void deleteCarts(String productId, String customerId) {

    }
}
