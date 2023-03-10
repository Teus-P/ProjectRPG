package com.teus.projectrpg.weapon.service.weaponreach;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.type.WeaponReachType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponReachService {

    List<BaseDto<WeaponReachType>> findAll();

}
