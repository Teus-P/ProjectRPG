package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.creaturetrait.dto.CreatureTraitDto;
import lombok.Data;

@Data
public class CharacterCreatureTraitDto {

    private Long id;
    private CreatureTraitDto trait;
    private String value;
}
