package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.*;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
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
    private List<BaseDto<BodyLocalizationType, BodyLocalizationEntity>> bodyLocalization;
    private Integer armorPoints;
    private List<BaseDto<ArmorPenaltyType, ArmorPenaltyEntity>> penalties;
    private List<BaseDto<ArmorQualityType, ArmorQualityEntity>> qualities;

    public ArmorDto() {
    }

    public ArmorDto(ArmorEntity armorEntity) {
        this.id = armorEntity.getId();
        this.name = armorEntity.getName();
        this.nameTranslation = armorEntity.getNameTranslation();
        this.armorCategory = new BaseDto<>(armorEntity.getArmorCategory());
        this.armorPoints = armorEntity.getArmorPoints();

        this.bodyLocalization = new ArrayList<>();
        for (BodyLocalizationEntity bodyLocalization: armorEntity.getBodyLocalizations()) {
            this.bodyLocalization.add(new BaseDto<>(bodyLocalization));
        }

        this.penalties = new ArrayList<>();
        for (ArmorPenaltyEntity armorPenalty: armorEntity.getArmorPenalties()) {
            this.penalties.add(new BaseDto<>(armorPenalty));
        }

        this.qualities = new ArrayList<>();
        for (ArmorQualityEntity armorQuality: armorEntity.getArmorQualities()) {
            this.qualities.add(new BaseDto<>(armorQuality));
        }
    }
}
