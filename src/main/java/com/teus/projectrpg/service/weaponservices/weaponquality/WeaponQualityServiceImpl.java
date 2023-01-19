package com.teus.projectrpg.service.weaponservices.weaponquality;

import com.teus.projectrpg.entity.weapon.WeaponQualityEntity;
import com.teus.projectrpg.repository.weapon.WeaponQualityRepository;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponQualityServiceImpl implements WeaponQualityService {

    private final WeaponQualityRepository weaponQualityRepository;

    @Autowired
    public WeaponQualityServiceImpl(WeaponQualityRepository weaponQualityRepository) {
        this.weaponQualityRepository = weaponQualityRepository;
    }

    @Override
    public List<WeaponQualityEntity> findAll() {
        return weaponQualityRepository.findAll();
    }

    @Override
    public WeaponQualityEntity findByType(WeaponQualityType weaponQualityType) {
        return weaponQualityRepository.findWeaponQualityEntityByName(weaponQualityType);
    }
}
