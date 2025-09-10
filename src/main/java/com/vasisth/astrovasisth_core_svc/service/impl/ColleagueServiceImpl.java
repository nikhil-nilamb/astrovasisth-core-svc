package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.dto.ColleagueRequest;
import com.vasisth.astrovasisth_core_svc.dto.ColleagueResponse;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.repo.ColleagueRepository;
import com.vasisth.astrovasisth_core_svc.service.ColleagueService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ColleagueServiceImpl implements ColleagueService {

    private final ColleagueRepository colleagueRepository;

    @Override
    public List<ColleagueResponse> getAllColleagues() {
        return colleagueRepository.findAll()
                .stream()
                .map(ColleagueServiceImpl::getColleagueResponse)
                .toList();
    }

    @Override
    public ColleagueResponse getColleagueById(String id) {
        return colleagueRepository.findById(UUID.fromString(id))
                .map(ColleagueServiceImpl::getColleagueResponse)
                .orElseThrow(() -> new CustomException("Colleague not found with id: " + id));
    }

    @Override
    public ColleagueResponse createColleague(ColleagueRequest colleagueRequest) {
        var entity = new Colleague();
        entity.setId(colleagueRequest.getId());
        entity.setFirstName(colleagueRequest.getFirstName());
        entity.setLastName(colleagueRequest.getLastName());
        entity.setEmail(colleagueRequest.getEmail());
        entity.setMobile(colleagueRequest.getMobile());
        entity.setDesignation(colleagueRequest.getDesignation());
        entity.setLinkedInProfile(colleagueRequest.getLinkedInProfile());
        entity.setSkills(colleagueRequest.getSkills());
        entity.setExperience(colleagueRequest.getExperience());
        entity.setLocation(colleagueRequest.getLocation());
        entity.setProfileImageUrl(colleagueRequest.getProfileImageUrl());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        entity.setUpdatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        var savedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(entity);
    }

    @Override
    public ColleagueResponse updateColleague(String id, ColleagueRequest colleagueRequest) {
        var entity = colleagueRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Colleague not found with id: " + id));
        entity.setFirstName(colleagueRequest.getFirstName());
        entity.setLastName(colleagueRequest.getLastName());
        entity.setEmail(colleagueRequest.getEmail());
        entity.setMobile(colleagueRequest.getMobile());
        entity.setDesignation(colleagueRequest.getDesignation());
        entity.setLinkedInProfile(colleagueRequest.getLinkedInProfile());
        entity.setSkills(colleagueRequest.getSkills());
        entity.setExperience(colleagueRequest.getExperience());
        entity.setLocation(colleagueRequest.getLocation());
        entity.setProfileImageUrl(colleagueRequest.getProfileImageUrl());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        var updatedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(entity);
    }

    @Override
    public void deleteColleague(String id) {
        if (!colleagueRepository.existsById(UUID.fromString(id))) {
            throw new CustomException("Colleague not found with id: " + id);
        }
        colleagueRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public ColleagueResponse approveColleague(String id) {
        var entity = colleagueRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CustomException("Colleague not found with id: " + id));
        entity.setApproveCount(entity.getApproveCount()+1);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        if(entity.getVerifiedBy() == null){
            entity.setVerifiedBy(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        }else{
            if(entity.getVerifiedBy().contains(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())){
                throw new CustomException("You have already approved this colleague");
            }else{
                entity.setVerifiedBy(entity.getVerifiedBy() + "," + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        entity.setActive(entity.getApproveCount() > 1);
        var updatedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(updatedEntity);
    }

    @Override
    public ColleagueResponse blockColleague(String id) {
        var entity = colleagueRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new CustomException("Colleague not found with id: " + id));
        entity.setActive(false);
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        entity.setVerifiedBy("");
        entity.setApproveCount(0);
        var updatedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(updatedEntity);
    }

    private static ColleagueResponse getColleagueResponse(Colleague entity) {
        ColleagueResponse response = new ColleagueResponse();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setEmail(entity.getEmail());
        response.setMobile(entity.getMobile());
        response.setDesignation(entity.getDesignation());
        response.setLinkedInProfile(entity.getLinkedInProfile());
        response.setSkills(entity.getSkills());
        response.setExperience(entity.getExperience());
        response.setLocation(entity.getLocation());
        response.setProfileImageUrl(entity.getProfileImageUrl());
        response.setApproveCount(entity.getApproveCount());
        return response;
    }
}
