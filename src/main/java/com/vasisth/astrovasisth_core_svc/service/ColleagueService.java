package com.vasisth.astrovasisth_core_svc.service;

import com.vasisth.astrovasisth_core_svc.dto.ColleagueRequest;
import com.vasisth.astrovasisth_core_svc.dto.ColleagueResponse;

import java.util.List;


public interface ColleagueService {
    List<ColleagueResponse> getAllColleagues();
    ColleagueResponse getColleagueById(String id);
    ColleagueResponse createColleague(ColleagueRequest colleagueRequest);
    ColleagueResponse updateColleague(String id, ColleagueRequest colleagueRequest);
    void deleteColleague(String id);
    ColleagueResponse approveColleague(String id);
    ColleagueResponse blockColleague(String id);
}
