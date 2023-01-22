package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.character.CharacterCreatureTraitEntity;
import lombok.Data;

@Data
public class CharacterCreatureTraitDto {

    private Long id;
    private CreatureTraitDto trait;
    private String value;

    public CharacterCreatureTraitDto() {
    }

    public CharacterCreatureTraitDto(CharacterCreatureTraitEntity traitEntity) {
        this.id = traitEntity.getId();
        this.trait = new CreatureTraitDto(traitEntity.getTrait());
        this.value = traitEntity.getValue();
    }
}
