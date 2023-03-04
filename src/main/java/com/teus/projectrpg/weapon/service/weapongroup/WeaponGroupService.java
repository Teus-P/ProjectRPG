package com.teus.projectrpg.weapon.service.weapongroup;

import com.teus.projectrpg.weapon.entity.WeaponGroupEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponGroupService {

    List<WeaponGroupEntity> findAll();
}
