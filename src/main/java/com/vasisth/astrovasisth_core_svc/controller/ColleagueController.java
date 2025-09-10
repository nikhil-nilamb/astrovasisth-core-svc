package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.dto.ColleagueRequest;
import com.vasisth.astrovasisth_core_svc.service.ColleagueService;
import com.vasisth.astrovasisth_core_svc.dto.ColleagueResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/colleagues")
public class ColleagueController {


    private final ColleagueService colleagueService;

    @GetMapping
    public ResponseEntity<List<ColleagueResponse>> getAllColleagues() {
        return ResponseEntity.ok(colleagueService.getAllColleagues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColleagueResponse> getColleagueById(@PathVariable String id) {
        return ResponseEntity.ok(colleagueService.getColleagueById(id));
    }

    @PostMapping
    public ResponseEntity<ColleagueResponse> createColleague(@RequestBody ColleagueRequest colleagueRequest) {
        return ResponseEntity.ok(colleagueService.createColleague(colleagueRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColleagueResponse> updateColleague(@PathVariable String id, @RequestBody ColleagueRequest colleagueRequest) {
        return ResponseEntity.ok(colleagueService.updateColleague(id, colleagueRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColleague(@PathVariable String id) {
        colleagueService.deleteColleague(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ColleagueResponse> approveColleague(@PathVariable String id) {
        return ResponseEntity.ok(colleagueService.approveColleague(id));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<ColleagueResponse> blockColleague(@PathVariable String id) {
        return ResponseEntity.ok(colleagueService.blockColleague(id));
    }
}