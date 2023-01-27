package com.teus.projectrpg.dto;

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
    private BaseDto<ArmorCategoryType> armorCategory;
    private List<ArmorBodyLocalizationDto> armorBodyLocalizations;
    private List<BaseDto<ArmorPenaltyType>> armorPenalties;
    private List<BaseDto<ArmorQualityType>> armorQualities;

    public ArmorDto() {
    }
}
