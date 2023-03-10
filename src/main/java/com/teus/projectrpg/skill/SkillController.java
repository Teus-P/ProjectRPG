package com.teus.projectrpg.skill;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.skill.service.SkillService;
import com.teus.projectrpg.skill.type.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/skill")
    public ResponseEntity<List<BaseDto<SkillType>>> getAllSkills() {
        List<BaseDto<SkillType>> skillDtos = skillService.findAll();
        if (skillDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(skillDtos);
    }
}
