package com.teus.projectrpg.service.weaponservices.weapongroup;

import com.teus.projectrpg.entity.weapon.WeaponGroupEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponGroupService {

    List<WeaponGroupEntity> findAll();
}
