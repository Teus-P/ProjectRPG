package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.ArmorEntity;
import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.ArmorQualityEntity;
import com.teus.projectrpg.entity.BodyLocalizationEntity;
import com.teus.projectrpg.type.ArmorCategory;
import com.teus.projectrpg.type.ArmorPenaltyType;
import com.teus.projectrpg.type.ArmorQualityType;
import com.teus.projectrpg.type.BodyLocalizationType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArmorDto implements Serializable {
    private Long id;
    private String name;
    private ArmorCategory armorCategory;
    private List<BodyLocalizationType> bodyLocalization;
    private Integer armorPoints;
    private List<ArmorPenaltyType> penalties;
    private List<ArmorQualityType> qualities;

    public ArmorDto() {
    }

    public ArmorDto(ArmorEntity armorEntity) {
        this.id = armorEntity.getId();
        this.name = armorEntity.getName();
        this.armorCategory = armorEntity.getArmorCategory();
        this.armorPoints = armorEntity.getArmorPoints();

        this.bodyLocalization = new ArrayList<>();
        for (BodyLocalizationEntity bodyLocalization: armorEntity.getBodyLocalizationEntities()) {
            this.bodyLocalization.add(bodyLocalization.getBodyLocalization());
        }

        this.penalties = new ArrayList<>();
        for (ArmorPenaltyEntity armorPenalty: armorEntity.getArmorPenalties()) {
            this.penalties.add(armorPenalty.getArmorPenaltyType());
        }

        this.qualities = new ArrayList<>();
        for (ArmorQualityEntity armorQuality: armorEntity.getArmorQualities()) {
            this.qualities.add(armorQuality.getArmorQualityType());
        }
    }
}
