package com.teus.projectrpg.armor.service.armorquality;

import com.teus.projectrpg.armor.entity.ArmorQualityEntity;
import com.teus.projectrpg.armor.type.ArmorQualityType;
import com.teus.projectrpg.base.dto.BaseDto;

import java.util.List;

public interface ArmorQualityService {

    List<BaseDto<ArmorQualityType>> findAll();

    void save(ArmorQualityEntity armorQuality);

}
