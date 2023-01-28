package com.teus.projectrpg.service.weapon.weapontype;

import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponTypeService {

    List<WeaponTypeEntity> findAll();
}
