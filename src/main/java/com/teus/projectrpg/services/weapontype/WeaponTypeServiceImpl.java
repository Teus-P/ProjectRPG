package com.teus.projectrpg.services.weapontype;

import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import com.teus.projectrpg.repository.weapon.WeaponTypeRepository;
import com.teus.projectrpg.type.weapon.WeaponType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponTypeServiceImpl implements WeaponTypeService{

    private final WeaponTypeRepository weaponTypeRepository;

    @Autowired
    public WeaponTypeServiceImpl(WeaponTypeRepository weaponTypeRepository) {
        this.weaponTypeRepository = weaponTypeRepository;
    }

    @Override
    public List<WeaponTypeEntity> findAll() {
        return weaponTypeRepository.findAll();
    }

    @Override
    public WeaponTypeEntity findByName(WeaponType weaponType) {
        return weaponTypeRepository.findWeaponTypeEntityByName(weaponType);
    }
}
