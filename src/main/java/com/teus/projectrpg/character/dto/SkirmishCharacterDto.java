package com.teus.projectrpg.character.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SkirmishCharacterDto extends CharacterDto {
    private int currentWounds;
    private int skirmishInitiative;
    private int advantage;
    private Boolean isDead;
    private int sequenceNumber;
}
