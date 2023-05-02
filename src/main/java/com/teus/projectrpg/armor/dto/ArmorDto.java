package com.teus.projectrpg.armor.dto;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.availability.type.AvailabilityType;
import com.teus.projectrpg.armor.type.ArmorCategoryType;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import com.teus.projectrpg.armor.type.ArmorQualityType;
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
    private String price;
    private String encumbrance;
    private BaseDto<AvailabilityType> availability;
    private Boolean isBaseArmor;
}
