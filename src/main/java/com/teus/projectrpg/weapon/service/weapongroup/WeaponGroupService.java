package com.teus.projectrpg.weapon.service.weapongroup;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponGroupService {

    List<BaseDto<WeaponGroupType>> findAll();
}
