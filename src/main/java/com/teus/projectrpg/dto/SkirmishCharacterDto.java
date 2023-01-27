package com.teus.projectrpg.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SkirmishCharacterDto extends CharacterDto {
    private int currentWounds;
    private int skirmishInitiative;
    private int advantage;
    private Boolean isDead;

    public SkirmishCharacterDto() {
    }
}
