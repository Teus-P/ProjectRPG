package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import lombok.Data;

@Data
public class ArmorBodyLocalizationDto {
    private Long id;
    private BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalization;
    private int armorPoints;

    public ArmorBodyLocalizationDto() {
    }

    public ArmorBodyLocalizationDto(ArmorBodyLocalizationEntity armorBodyLocalizationEntity) {
        this.id = armorBodyLocalizationEntity.getId();
        this.bodyLocalization = new BaseDto<>(armorBodyLocalizationEntity.getBodyLocalization());
        this.armorPoints = armorBodyLocalizationEntity.getArmorPoints();
    }
}
