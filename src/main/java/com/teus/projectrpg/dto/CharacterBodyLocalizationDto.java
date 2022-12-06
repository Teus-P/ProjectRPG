package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.entity.injury.CharacterBodyLocalizationInjuryEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CharacterBodyLocalizationDto {
    private Long id;
    private Long characterId;
    private BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalization;
    private int armorPoints;
    private int additionalArmorPoints;
    private List<CharacterBodyLocalizationInjuryDto> injuries;

    public CharacterBodyLocalizationDto() {
    }

    public CharacterBodyLocalizationDto(CharacterBodyLocalizationEntity characterBodyLocalization) {
        this.id = characterBodyLocalization.getId();
        this.characterId = characterBodyLocalization.getCharacter().getId();
        this.bodyLocalization = new BaseDto<>(characterBodyLocalization.getBodyLocalization());
        this.armorPoints = characterBodyLocalization.getArmorPoints();
        this.additionalArmorPoints = characterBodyLocalization.getAdditionalArmorPoints();

        List<CharacterBodyLocalizationInjuryDto> injuries = new ArrayList<>();
        for (CharacterBodyLocalizationInjuryEntity injury : characterBodyLocalization.getInjuries()) {
            injuries.add(new CharacterBodyLocalizationInjuryDto(injury));
        }
        this.injuries = injuries;

    }
}
