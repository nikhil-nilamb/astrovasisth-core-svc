package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColleagueRepository  extends JpaRepository<Colleague, UUID> {
    List<Colleague> findByRoleAndIsActive(Role role,boolean isActive);
    Optional<Colleague> findBymobile(String mobile);
    Optional<Colleague> findByEmailOrMobile(String email,String mobile);
    List<Colleague> findByIdInAndIsActiveTrue(List<UUID> ids);

}
