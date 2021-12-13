package com.teus.projectrpg.services.armorquality;

import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.ArmorQualityEntity;
import com.teus.projectrpg.type.ArmorQualityType;

import java.util.List;

public interface ArmorQualityService {

    List<ArmorQualityEntity> findAll();

    void save(ArmorQualityEntity armorQuality);

    ArmorQualityEntity findByType(ArmorQualityType armorQualityType);

}
