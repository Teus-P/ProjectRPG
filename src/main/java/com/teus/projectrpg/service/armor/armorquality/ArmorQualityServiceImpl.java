package com.teus.projectrpg.service.armor.armorquality;

import com.teus.projectrpg.entity.armor.ArmorQualityEntity;
import com.teus.projectrpg.repository.armor.ArmorQualityRepository;
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
