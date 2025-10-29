package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmailOrMobileAndIsDeletedFalse(String email, String mobile);
    List<Customer> findByIdInAndIsDeletedFalse(List<UUID> ids);
    Optional<Customer> findByMobileAndIsDeletedFalse(String mobile);
//    Optional<Customer> findByEmailAndIsDeletedFalse(String email);

//    List<Customer> findByIsDeletedFalse();
//    List<Customer> findByIsActiveTrueAndIsDeletedFalse();
//    Optional<Customer> findByIdAndIsDeletedFalse(UUID id);
//    boolean existsByEmailAndIsDeletedFalse(String email);
//    boolean existsByMobileAndIsDeletedFalse(String mobile);
}
