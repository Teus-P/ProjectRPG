package com.teus.projectrpg.services.armorquality;

import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.entity.ArmorQualityEntity;
import com.teus.projectrpg.repository.ArmorPenaltyRepository;
import com.teus.projectrpg.repository.ArmorQualityRepository;
import com.teus.projectrpg.type.ArmorPenaltyType;
import com.teus.projectrpg.type.ArmorQualityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorQualityServiceImpl implements ArmorQualityService {

    private final ArmorQualityRepository armorQualityRepository;

    @Autowired
    public ArmorQualityServiceImpl(ArmorQualityRepository armorQualityRepository) {
        this.armorQualityRepository = armorQualityRepository;
    }

    @Override
    public List<ArmorQualityEntity> findAll() {
        return armorQualityRepository.findAll();
    }

    @Override
    public void save(ArmorQualityEntity armorQualityEntity) {
        armorQualityRepository.save(armorQualityEntity);
    }

    @Override
    public ArmorQualityEntity findByType(ArmorQualityType armorQualityType) {
        return armorQualityRepository.findArmorQualityByArmorQualityType(armorQualityType);
    }
}
