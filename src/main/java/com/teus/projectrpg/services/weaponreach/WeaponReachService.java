package com.teus.projectrpg.services.weaponreach;

import com.teus.projectrpg.entity.weapon.WeaponReachEntity;
import com.teus.projectrpg.type.weapon.WeaponReachType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponReachService {

    List<WeaponReachEntity> findAll();

    WeaponReachEntity findByName(WeaponReachType weaponReachType);

}
