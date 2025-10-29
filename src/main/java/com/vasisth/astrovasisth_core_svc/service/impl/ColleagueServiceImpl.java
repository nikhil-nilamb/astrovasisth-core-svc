package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.constants.ColleagueProfileStatus;
import com.vasisth.astrovasisth_core_svc.constants.Role;
import com.vasisth.astrovasisth_core_svc.dto.ColleagueRequest;
import com.vasisth.astrovasisth_core_svc.dto.ColleagueResponse;
import com.vasisth.astrovasisth_core_svc.entity.Colleague;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.repo.ColleagueRepository;
import com.vasisth.astrovasisth_core_svc.service.ColleagueService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ColleagueServiceImpl implements ColleagueService {

    private final ColleagueRepository colleagueRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

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
        entity.setPassword(passwordEncoder.encode(generateRandomPassword()));
        entity.setDob(colleagueRequest.getDob());
        entity.setGender(colleagueRequest.getGender());
        entity.setQualification(colleagueRequest.getQualification());
        entity.setYearOfPassout(colleagueRequest.getYearOfPassout());
        entity.setWorkingSince(colleagueRequest.getWorkingSince());
        entity.setEmail(colleagueRequest.getEmail());
        entity.setMobile(colleagueRequest.getMobile());
        entity.setDesignation(colleagueRequest.getDesignation());
        entity.setSkills(colleagueRequest.getSkills());
        entity.setAddress(colleagueRequest.getAddress());
        entity.setRole(Role.valueOf(colleagueRequest.getRole()));
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setCreatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        entity.setUpdatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        entity.setProfileStatus(ColleagueProfileStatus.ADDED);
        var savedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(entity);
    }

    @Override
    public ColleagueResponse updateColleague(String id, ColleagueRequest colleagueRequest) {
        var entity = colleagueRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Colleague not found with id: " + id));
        entity.setId(colleagueRequest.getId());
        entity.setFirstName(colleagueRequest.getFirstName());
        entity.setLastName(colleagueRequest.getLastName());
        entity.setPassword(colleagueRequest.getPassword() != null ?  passwordEncoder.encode(colleagueRequest.getPassword()) : entity.getPassword());
        entity.setDob(colleagueRequest.getDob());
        entity.setGender(colleagueRequest.getGender());
        entity.setQualification(colleagueRequest.getQualification());
        entity.setYearOfPassout(colleagueRequest.getYearOfPassout());
        entity.setWorkingSince(colleagueRequest.getWorkingSince());
        entity.setEmail(colleagueRequest.getEmail());
        entity.setMobile(colleagueRequest.getMobile());
        entity.setDesignation(colleagueRequest.getDesignation());
        entity.setSkills(colleagueRequest.getSkills());
        entity.setAddress(colleagueRequest.getAddress());
        entity.setRole(Role.valueOf(colleagueRequest.getRole()));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setProfileStatus(ColleagueProfileStatus.INCOMPLETE);
        entity.setApproveCount(0);
        entity.setVerifiedBy(null);
        entity.setUpdatedBy(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
        var updatedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(entity);
    }

    @Override
    public void deleteColleague(String id) {
        var entity = colleagueRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Colleague not found with id: " + id));
        entity.setProfileStatus(ColleagueProfileStatus.REJECTED);
        colleagueRepository.save(entity);
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
        }else {
            if (entity.getVerifiedBy().contains(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())) {
                throw new CustomException("You have already approved this colleague");
            } else {
                entity.setVerifiedBy(entity.getVerifiedBy() + "," + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        entity.setProfileStatus(entity.getApproveCount() > 1 ? ColleagueProfileStatus.APPROVED : ColleagueProfileStatus.INCOMPLETE);
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
        entity.setProfileStatus(ColleagueProfileStatus.BLOCKED);

        var updatedEntity = colleagueRepository.save(entity);
        return getColleagueResponse(updatedEntity);
    }

    @Override
    public List<ColleagueResponse> getAllActiveVasisth() {
        return  colleagueRepository.findByRoleAndIsActive(Role.VASISTH,true)
                .stream()
                .map(ColleagueServiceImpl::getColleagueResponse)
                .toList();
    }

    private static ColleagueResponse getColleagueResponse(Colleague entity) {
        ColleagueResponse response = new ColleagueResponse();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setDob(entity.getDob());
        response.setGender(entity.getGender());
        response.setQualification(entity.getQualification());
        response.setYearOfPassout(entity.getYearOfPassout());
        response.setWorkingSince(entity.getWorkingSince());
        response.setEmail(entity.getEmail());
        response.setMobile(entity.getMobile());
        response.setDesignation(entity.getDesignation());
        response.setSkills(entity.getSkills());
        response.setAddress(entity.getAddress());
        response.setApproveCount(entity.getApproveCount());
        response.setRole(entity.getRole().name());
        response.setProfileStatus(entity.getProfileStatus().name());
        response.setApprovedBy(entity.getVerifiedBy());
        return response;
    }

    public static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        System.out.println(password.toString());

        return password.toString();
    }
}
