package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.skill.dto.SkillDto;
import lombok.Data;

@Data
public class CharacterSkillDto {
    private Long id;
    private SkillDto model;
    private int value;
    private String specialisation;
}
