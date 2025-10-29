package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.dto.ProductReqRes;

import java.util.List;

public interface ProductService {
    List<ProductReqRes> getAllProducts();
    ProductReqRes getProductInfo(String productId);
    ProductReqRes createProduct(ProductReqRes productReqRes);
    ProductReqRes updateProduct(String productId, ProductReqRes productReqRes);
    void deleteProduct(String productId);

}
