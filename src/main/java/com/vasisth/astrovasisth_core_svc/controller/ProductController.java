package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.dto.ProductReqRes;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductReqRes> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public ProductReqRes getProductById(@PathVariable String id) {
        return productService.getProductInfo(id);
    }

    @PostMapping
    public ProductReqRes createProduct(@RequestBody ProductReqRes product) {
        return productService.createProduct(product);
    }

    @PutMapping("{id}")
    public ProductReqRes updateProduct(@PathVariable String id, @RequestBody ProductReqRes product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}