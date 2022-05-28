package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.*;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArmorDto implements Serializable {

    private Long id;
    private String name;
    private String nameTranslation;
    private BaseDto<ArmorCategoryType, ArmorCategoryEntity> armorCategory;
    private List<ArmorBodyLocalizationDto> armorBodyLocalizations;
    private Integer armorPoints;
    private List<BaseDto<ArmorPenaltyType, ArmorPenaltyEntity>> armorPenalties;
    private List<BaseDto<ArmorQualityType, ArmorQualityEntity>> armorQualities;

    public ArmorDto() {
    }

    public ArmorDto(ArmorEntity armorEntity) {
        this.id = armorEntity.getId();
        this.name = armorEntity.getName();
        this.nameTranslation = armorEntity.getNameTranslation();
        this.armorCategory = new BaseDto<>(armorEntity.getArmorCategory());

        this.armorBodyLocalizations = new ArrayList<>();
        for (ArmorBodyLocalizationEntity armorBodyLocalization: armorEntity.getArmorBodyLocalizations()) {
            this.armorBodyLocalizations.add(new ArmorBodyLocalizationDto(armorBodyLocalization));
        }

        this.armorPenalties = new ArrayList<>();
        for (ArmorPenaltyEntity armorPenalty: armorEntity.getArmorPenalties()) {
            this.armorPenalties.add(new BaseDto<>(armorPenalty));
        }

        this.armorQualities = new ArrayList<>();
        for (ArmorQualityEntity armorQuality: armorEntity.getArmorQualities()) {
            this.armorQualities.add(new BaseDto<>(armorQuality));
        }
    }
}
