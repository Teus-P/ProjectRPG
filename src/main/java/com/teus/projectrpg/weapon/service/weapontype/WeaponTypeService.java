package com.teus.projectrpg.weapon.service.weapontype;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.type.WeaponType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponTypeService {

    List<BaseDto<WeaponType>> findAll();
}
