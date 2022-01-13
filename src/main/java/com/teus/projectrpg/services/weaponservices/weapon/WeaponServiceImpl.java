package com.teus.projectrpg.services.weaponservices.weapon;

import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.repository.weapon.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;

    @Autowired
    public WeaponServiceImpl(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    @Override
    public List<WeaponEntity> findAll() {
        return weaponRepository.findAll();
    }

    @Override
    public WeaponEntity save(WeaponEntity weaponEntity) {
        return weaponRepository.save(weaponEntity);
    }
}
