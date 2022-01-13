package com.teus.projectrpg.services.weaponservices.weapongroup;

import com.teus.projectrpg.entity.weapon.WeaponGroupEntity;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponGroupService{
    List<WeaponGroupEntity> findAll();
    WeaponGroupEntity findByName(WeaponGroupType weaponGroupType);
}
