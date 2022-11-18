package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.character.CharacterTalentEntity;
import lombok.Data;

@Data
public class CharacterTalentDto {
    private Long id;
    private TalentDto talent;
    private int value;

    public CharacterTalentDto() {
    }

    public CharacterTalentDto(CharacterTalentEntity characterTalentEntity) {
        this.id = characterTalentEntity.getId();
        this.talent = new TalentDto(characterTalentEntity.getTalent());
        this.value = characterTalentEntity.getValue();
    }
}
