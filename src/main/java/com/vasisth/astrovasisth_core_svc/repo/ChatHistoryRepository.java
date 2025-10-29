package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.entity.ChatHistory;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.entity.Customer;
import com.vasisth.astrovasisth_core_svc.service.ChatHistoryService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, UUID> {
    List<ChatHistory> findByCustomerId(UUID customerId);
    Optional<ChatHistory> findByCustomerIdAndColleagueId(UUID customerId, UUID colleagueId);
//    List<ChatHistory> findByColleagueId(UUID colleagueId);
}
