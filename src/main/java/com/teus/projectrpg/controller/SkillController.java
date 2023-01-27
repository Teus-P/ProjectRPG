package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.mapper.base.BaseMapper;
import com.teus.projectrpg.service.skill.SkillService;
import com.teus.projectrpg.type.skill.SkillType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SkillController {

    private final SkillService skillService;
    private final BaseMapper baseMapper;

    public SkillController(SkillService skillService, BaseMapper baseMapper) {
        this.skillService = skillService;
        this.baseMapper = baseMapper;
    }

    @GetMapping("/skill")
    List<BaseDto<SkillType>> getAllSkills() {
        return baseMapper.toDtos(skillService.findAll());
    }
}
