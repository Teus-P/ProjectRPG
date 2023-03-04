package com.teus.projectrpg.weapon.service.weaponquality;

import com.teus.projectrpg.weapon.entity.WeaponQualityEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponQualityService {

    List<WeaponQualityEntity> findAll();
}
