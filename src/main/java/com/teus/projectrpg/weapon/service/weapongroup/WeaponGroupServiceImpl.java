package com.teus.projectrpg.weapon.service.weapongroup;

import com.teus.projectrpg.weapon.entity.WeaponGroupEntity;
import com.teus.projectrpg.weapon.repository.WeaponGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponGroupServiceImpl implements WeaponGroupService {

    private final WeaponGroupRepository weaponGroupRepository;

    @Override
    public List<WeaponGroupEntity> findAll() {
        return weaponGroupRepository.findAll();
    }
}
