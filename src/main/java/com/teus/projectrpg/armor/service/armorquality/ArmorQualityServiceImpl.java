package com.teus.projectrpg.armor.service.armorquality;

import com.teus.projectrpg.armor.entity.ArmorQualityEntity;
import com.teus.projectrpg.armor.repository.ArmorQualityRepository;
import com.teus.projectrpg.armor.type.ArmorQualityType;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorQualityServiceImpl implements ArmorQualityService {

    private final ArmorQualityRepository armorQualityRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<ArmorQualityType>> findAll() {
        return this.baseMapper.toDtos(armorQualityRepository.findAll());
    }

    @Override
    public void save(ArmorQualityEntity armorQualityEntity) {
        armorQualityRepository.save(armorQualityEntity);
    }
}
