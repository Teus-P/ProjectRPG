package com.teus.projectrpg.weapon.service.weapontype;

import com.teus.projectrpg.weapon.entity.WeaponTypeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponTypeService {

    List<WeaponTypeEntity> findAll();
}
