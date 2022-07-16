package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.entity.injury.CharacterBodyLocalizationInjuryEntity;
import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import com.teus.projectrpg.type.injury.InjuryType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CharacterBodyLocalizationDto {
    private Long id;
    private BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalization;
    private int armorPoints;
    private List<CharacterBodyLocalizationInjuryDto> injuries;

    public CharacterBodyLocalizationDto() {
    }

    public CharacterBodyLocalizationDto(CharacterBodyLocalizationEntity characterBodyLocalization) {
        this.id = characterBodyLocalization.getId();
        this.bodyLocalization = new BaseDto<>(characterBodyLocalization.getBodyLocalization());
        this.armorPoints = characterBodyLocalization.getArmorPoints();

        List<CharacterBodyLocalizationInjuryDto> injuries = new ArrayList<>();
        for (CharacterBodyLocalizationInjuryEntity injury : characterBodyLocalization.getInjuries()) {
            injuries.add(new CharacterBodyLocalizationInjuryDto(injury));
        }
        this.injuries = injuries;

    }
}
