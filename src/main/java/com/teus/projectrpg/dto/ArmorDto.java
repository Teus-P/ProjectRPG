package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.armor.ArmorCategoryEntity;
import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.type.armor.ArmorCategoryType;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import com.teus.projectrpg.type.armor.ArmorQualityType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArmorDto implements Serializable {

    private Long id;
    private String name;
    private String nameTranslation;
    private BaseDto<ArmorCategoryType, ArmorCategoryEntity> armorCategory;
    private List<ArmorBodyLocalizationDto> armorBodyLocalizations;
    private List<BaseDto<ArmorPenaltyType, ArmorPenaltyEntity>> armorPenalties;
    private List<BaseDto<ArmorQualityType, ArmorQualityEntity>> armorQualities;

    public ArmorDto() {
    }
}
