package com.teus.projectrpg.weapon.service.weapontype;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.weapon.repository.WeaponTypeRepository;
import com.teus.projectrpg.weapon.type.WeaponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponTypeServiceImpl implements WeaponTypeService {

    private final WeaponTypeRepository weaponTypeRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<WeaponType>> findAll() {
        return baseMapper.toDtos(weaponTypeRepository.findAll());
    }
}
