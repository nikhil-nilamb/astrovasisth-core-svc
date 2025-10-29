package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.entity.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartsRepository extends JpaRepository<Carts, UUID> {
}
