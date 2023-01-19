package com.teus.projectrpg.service.armorservices.armorquality;

import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.type.armor.ArmorQualityType;

import java.util.List;

public interface ArmorQualityService {

    List<ArmorQualityEntity> findAll();

    void save(ArmorQualityEntity armorQuality);

    ArmorQualityEntity findByType(ArmorQualityType armorQualityType);

}
