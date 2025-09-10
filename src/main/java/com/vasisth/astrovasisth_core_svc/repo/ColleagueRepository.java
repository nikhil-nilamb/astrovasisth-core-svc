package com.vasisth.astrovasisth_core_svc.repo;

import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ColleagueRepository  extends JpaRepository<Colleague, UUID> {
}
