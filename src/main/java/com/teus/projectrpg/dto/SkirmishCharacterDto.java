package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import lombok.Data;

@Data
public class SkirmishCharacterDto extends CharacterDto {
    private int currentWounds;
    private int skirmishInitiative;
    private int advantage;
    private Boolean isDead;

    public SkirmishCharacterDto() {
    }

    public SkirmishCharacterDto(SkirmishCharacterEntity skirmishCharacterEntity) {
        super(skirmishCharacterEntity);
        this.currentWounds = skirmishCharacterEntity.getCurrentWounds();
        this.skirmishInitiative = skirmishCharacterEntity.getSkirmishInitiative();
        this.advantage = skirmishCharacterEntity.getAdvantage();
        this.isDead = skirmishCharacterEntity.getIsDead();
    }
}
