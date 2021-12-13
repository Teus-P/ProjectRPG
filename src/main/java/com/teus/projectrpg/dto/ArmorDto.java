package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.ArmorCategory;
import com.teus.projectrpg.type.ArmorPenaltyType;
import com.teus.projectrpg.type.ArmorQualityType;
import com.teus.projectrpg.type.BodyLocalizationType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArmorDto implements Serializable {
    private final Long id;
    private final String name;
    private final ArmorCategory armorCategory;
    private final List<BodyLocalizationType> bodyLocalization;
    private final Integer armorPoints;
    private final List<ArmorPenaltyType> penalties;
    private final List<ArmorQualityType> qualities;
}
