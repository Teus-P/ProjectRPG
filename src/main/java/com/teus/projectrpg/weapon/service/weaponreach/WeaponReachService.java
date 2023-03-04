package com.teus.projectrpg.weapon.service.weaponreach;

import com.teus.projectrpg.weapon.entity.WeaponReachEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponReachService {

    List<WeaponReachEntity> findAll();

}
