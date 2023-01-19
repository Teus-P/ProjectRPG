package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.TalentDto;
import com.teus.projectrpg.entity.talent.TalentEntity;
import com.teus.projectrpg.service.talent.TalentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TalentController {

    private final TalentService talentService;

    public TalentController(TalentService talentService) {
        this.talentService = talentService;
    }

    @GetMapping("/talent")
    List<TalentDto> getAllTalents() {
        List<TalentDto> talentDtos = new ArrayList<>();

        for (TalentEntity talentEntity : talentService.findAll()) {
            talentDtos.add(new TalentDto(talentEntity));
        }

        return talentDtos;
    }
}
