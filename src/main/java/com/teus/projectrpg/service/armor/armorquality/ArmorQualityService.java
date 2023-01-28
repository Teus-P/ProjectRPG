package com.teus.projectrpg.service.armor.armorquality;

import com.teus.projectrpg.entity.armor.ArmorQualityEntity;

import java.util.List;

public interface ArmorQualityService {

    List<ArmorQualityEntity> findAll();

    void save(ArmorQualityEntity armorQuality);

}
