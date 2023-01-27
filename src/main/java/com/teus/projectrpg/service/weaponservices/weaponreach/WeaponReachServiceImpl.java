package com.teus.projectrpg.service.weaponservices.weaponreach;

import com.teus.projectrpg.entity.weapon.WeaponReachEntity;
import com.teus.projectrpg.repository.weapon.WeaponReachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponReachServiceImpl implements WeaponReachService {

    private final WeaponReachRepository weaponReachRepository;

    @Autowired
    public WeaponReachServiceImpl(WeaponReachRepository weaponReachRepository) {
        this.weaponReachRepository = weaponReachRepository;
    }

    @Override
    public List<WeaponReachEntity> findAll() {
        return weaponReachRepository.findAll();
    }
}
