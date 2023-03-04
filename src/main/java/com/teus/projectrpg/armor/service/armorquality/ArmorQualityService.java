package com.teus.projectrpg.armor.service.armorquality;

import com.teus.projectrpg.armor.entity.ArmorQualityEntity;

import java.util.List;

public interface ArmorQualityService {

    List<ArmorQualityEntity> findAll();

    void save(ArmorQualityEntity armorQuality);

}
