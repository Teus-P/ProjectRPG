package com.teus.projectrpg.dto;

import lombok.Data;

@Data
public class CharacterCreatureTraitDto {

    private Long id;
    private CreatureTraitDto trait;
    private String value;

    public CharacterCreatureTraitDto() {
    }
}
