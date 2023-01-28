package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.TalentDto;
import com.teus.projectrpg.mapper.TalentMapper;
import com.teus.projectrpg.service.talent.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TalentController {

    private final TalentService talentService;
    private final TalentMapper talentMapper;

    @GetMapping("/talent")
    List<TalentDto> getAllTalents() {
        return talentMapper.toDtos(talentService.findAll());
    }
}
