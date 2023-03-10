package com.teus.projectrpg.weapon.service.weaponreach;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.weapon.repository.WeaponReachRepository;
import com.teus.projectrpg.weapon.type.WeaponReachType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponReachServiceImpl implements WeaponReachService {

    private final WeaponReachRepository weaponReachRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<WeaponReachType>> findAll() {
        return baseMapper.toDtos(weaponReachRepository.findAll());
    }
}
