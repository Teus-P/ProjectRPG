package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.SkillDto;
import com.teus.projectrpg.entity.skill.SkillEntity;
import com.teus.projectrpg.services.skill.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/skill")
    List<SkillDto> getAllSkills() {
        List<SkillDto> skillDtos = new ArrayList<>();

        for(SkillEntity skillEntity : skillService.findAll()) {
            skillDtos.add(new SkillDto(skillEntity));
        }

        return skillDtos;
    }
}
