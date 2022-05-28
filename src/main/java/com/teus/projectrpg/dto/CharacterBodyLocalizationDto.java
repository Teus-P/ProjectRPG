package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import lombok.Data;

@Data
public class CharacterBodyLocalizationDto {
    private Long id;
    private BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalization;
    private int armorPoints;

    //must contain character's injuries for specific bodyLocalization

    public CharacterBodyLocalizationDto() {
    }

    public CharacterBodyLocalizationDto(CharacterBodyLocalizationEntity characterBodyLocalization) {
        this.id = characterBodyLocalization.getId();
        this.bodyLocalization = new BaseDto<>(characterBodyLocalization.getBodyLocalization());
        this.armorPoints = characterBodyLocalization.getArmorPoints();
    }
}
