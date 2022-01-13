package com.teus.projectrpg.services.weaponservices.weaponquality;

import com.teus.projectrpg.entity.weapon.WeaponQualityEntity;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponQualityService {

    List<WeaponQualityEntity> findAll();

    WeaponQualityEntity findByType(WeaponQualityType weaponQualityType);
}
