package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
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
    private ArmorCategoryType armorCategory;
    private List<BodyLocalizationType> bodyLocalization;
    private Integer armorPoints;
    private List<ArmorPenaltyType> penalties;
    private List<ArmorQualityType> qualities;

    public ArmorDto() {
    }

    public ArmorDto(ArmorEntity armorEntity) {
        this.id = armorEntity.getId();
        this.name = armorEntity.getName();
        this.nameTranslation = armorEntity.getNameTranslation();
        this.armorCategory = armorEntity.getArmorCategory().getName();
        this.armorPoints = armorEntity.getArmorPoints();

        this.bodyLocalization = new ArrayList<>();
        for (BodyLocalizationEntity bodyLocalization: armorEntity.getBodyLocalizationEntities()) {
            this.bodyLocalization.add(bodyLocalization.getName());
        }

        this.penalties = new ArrayList<>();
        for (ArmorPenaltyEntity armorPenalty: armorEntity.getArmorPenalties()) {
            this.penalties.add(armorPenalty.getName());
        }

        this.qualities = new ArrayList<>();
        for (ArmorQualityEntity armorQuality: armorEntity.getArmorQualities()) {
            this.qualities.add(armorQuality.getName());
        }
    }
}
