package com.teus.projectrpg.weapon.service.weaponquality;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponQualityService {

    List<BaseDto<WeaponQualityType>> findAll();
}
