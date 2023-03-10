package com.teus.projectrpg.weapon.service.weaponquality;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.weapon.repository.WeaponQualityRepository;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponQualityServiceImpl implements WeaponQualityService {

    private final WeaponQualityRepository weaponQualityRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<WeaponQualityType>> findAll() {
        return baseMapper.toDtos(weaponQualityRepository.findAll());
    }
}
