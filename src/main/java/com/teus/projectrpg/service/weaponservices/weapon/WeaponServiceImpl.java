package com.teus.projectrpg.service.weaponservices.weapon;

import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.repository.weapon.WeaponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;

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

    @Override
    public WeaponEntity findByName(String name) {
        return weaponRepository.findWeaponEntityByName(name);
    }

    @Override
    public void deleteById(Long id) {
        weaponRepository.deleteById(id);
    }
}
