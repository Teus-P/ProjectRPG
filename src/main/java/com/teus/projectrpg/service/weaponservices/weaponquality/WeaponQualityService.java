package com.teus.projectrpg.service.weaponservices.weaponquality;

import com.teus.projectrpg.entity.weapon.WeaponQualityEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponQualityService {

    List<WeaponQualityEntity> findAll();
}
