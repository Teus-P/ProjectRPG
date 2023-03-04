package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.talent.dto.TalentDto;
import lombok.Data;

@Data
public class CharacterTalentDto {
    private Long id;
    private TalentDto talent;
    private int value;
}
