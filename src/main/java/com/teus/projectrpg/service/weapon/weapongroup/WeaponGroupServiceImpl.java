package com.teus.projectrpg.service.weapon.weapongroup;

import com.teus.projectrpg.entity.weapon.WeaponGroupEntity;
import com.teus.projectrpg.repository.weapon.WeaponGroupRepository;
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
