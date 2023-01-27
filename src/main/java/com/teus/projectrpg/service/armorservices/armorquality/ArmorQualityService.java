package com.teus.projectrpg.service.armorservices.armorquality;

import com.teus.projectrpg.entity.armor.ArmorQualityEntity;

import java.util.List;

public interface ArmorQualityService {

    List<ArmorQualityEntity> findAll();

    void save(ArmorQualityEntity armorQuality);

}
