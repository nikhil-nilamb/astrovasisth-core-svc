package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}