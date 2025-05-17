package com.teus.projectrpg.skill;

import com.teus.projectrpg.skill.dto.SkillDto;
import com.teus.projectrpg.skill.service.SkillService;
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
    public ResponseEntity<List<SkillDto>> getAllSkills() {
        List<SkillDto> skillDtos = skillService.findAll();
        if (skillDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(skillDtos);
    }
}
