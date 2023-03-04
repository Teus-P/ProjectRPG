package com.teus.projectrpg.armor.service.armorquality;

import com.teus.projectrpg.armor.entity.ArmorQualityEntity;
import com.teus.projectrpg.armor.repository.ArmorQualityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorQualityServiceImpl implements ArmorQualityService {

    private final ArmorQualityRepository armorQualityRepository;

    @Override
    public List<ArmorQualityEntity> findAll() {
        return armorQualityRepository.findAll();
    }

    @Override
    public void save(ArmorQualityEntity armorQualityEntity) {
        armorQualityRepository.save(armorQualityEntity);
    }
}
