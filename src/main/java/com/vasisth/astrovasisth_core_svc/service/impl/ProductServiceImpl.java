package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.dto.ProductReqRes;
import com.vasisth.astrovasisth_core_svc.entity.Product;
import com.vasisth.astrovasisth_core_svc.repo.ProductRepository;
import com.vasisth.astrovasisth_core_svc.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductReqRes> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public ProductReqRes getProductInfo(String productId) {
        return productRepository.findById(UUID.fromString(productId)).map(this::mapToDTO).orElse(null);
    }

    @Override
    public ProductReqRes createProduct(ProductReqRes productReqRes) {
        Product product = new Product();
        product.setName(productReqRes.getName());
        product.setDescription(productReqRes.getDescription());
        product.setPrice(productReqRes.getPrice());
        productRepository.save(product);
        productReqRes.setId(product.getId());
        return  productReqRes;
    }

    @Override
    public ProductReqRes updateProduct(String productId, ProductReqRes productReqRes) {
        return productRepository.findById(UUID.fromString(productId)).map(existingProduct -> {
            existingProduct.setName(productReqRes.getName());
            existingProduct.setDescription(productReqRes.getDescription());
            existingProduct.setPrice(productReqRes.getPrice());
            productRepository.save(existingProduct);
            return mapToDTO(existingProduct);
        }).orElse(null);
    }

    @Override
    public void deleteProduct(String productId) {
        productRepository.deleteById(UUID.fromString(productId));
    }

    private ProductReqRes mapToDTO(Product product) {
        ProductReqRes productReqRes = new ProductReqRes();
        productReqRes.setId(product.getId());
        productReqRes.setName(product.getName());
        productReqRes.setDescription(product.getDescription());
        productReqRes.setPrice(product.getPrice());
        productReqRes.setImageUrls(product.getImageUrls());
        return productReqRes;
    }


}
