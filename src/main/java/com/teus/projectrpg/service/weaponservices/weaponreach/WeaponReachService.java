package com.teus.projectrpg.service.weaponservices.weaponreach;

import com.teus.projectrpg.entity.weapon.WeaponReachEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WeaponReachService {

    List<WeaponReachEntity> findAll();

}
