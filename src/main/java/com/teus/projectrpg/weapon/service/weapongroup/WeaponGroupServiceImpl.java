package com.teus.projectrpg.weapon.service.weapongroup;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.weapon.repository.WeaponGroupRepository;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponGroupServiceImpl implements WeaponGroupService {

    private final WeaponGroupRepository weaponGroupRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<WeaponGroupType>> findAll() {
        return baseMapper.toDtos(weaponGroupRepository.findAll());
    }
}
