package com.teus.projectrpg.service.weapon.weapon;

import com.teus.projectrpg.entity.weapon.WeaponEntity;
import com.teus.projectrpg.repository.weapon.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;

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
