package com.teus.projectrpg.skill;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.skill.service.SkillService;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final BaseMapper baseMapper;

    @GetMapping("/skill")
    List<BaseDto<SkillType>> getAllSkills() {
        return baseMapper.toDtos(skillService.findAll());
    }
}
