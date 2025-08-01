package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrMobile(String email,String mobile);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByEmail(String email);

}
