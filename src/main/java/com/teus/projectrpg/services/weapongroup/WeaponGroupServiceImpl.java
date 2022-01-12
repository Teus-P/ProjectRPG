package com.teus.projectrpg.services.weapongroup;

import com.teus.projectrpg.entity.weapon.WeaponGroupEntity;
import com.teus.projectrpg.repository.weapon.WeaponGroupRepository;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponGroupServiceImpl implements WeaponGroupService{

    private final WeaponGroupRepository weaponGroupRepository;

    @Autowired
    public WeaponGroupServiceImpl(WeaponGroupRepository weaponGroupRepository) {
        this.weaponGroupRepository = weaponGroupRepository;
    }

    @Override
    public List<WeaponGroupEntity> findAll() {
        return weaponGroupRepository.findAll();
    }

    @Override
    public WeaponGroupEntity findByName(WeaponGroupType weaponGroupType) {
        return weaponGroupRepository.findWeaponGroupEntityByName(weaponGroupType);
    }
}
