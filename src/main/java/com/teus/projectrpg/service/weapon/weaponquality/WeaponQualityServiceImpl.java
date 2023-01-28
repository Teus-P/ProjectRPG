package com.teus.projectrpg.service.weapon.weaponquality;

import com.teus.projectrpg.entity.weapon.WeaponQualityEntity;
import com.teus.projectrpg.repository.weapon.WeaponQualityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponQualityServiceImpl implements WeaponQualityService {

    private final WeaponQualityRepository weaponQualityRepository;

    @Override
    public List<WeaponQualityEntity> findAll() {
        return weaponQualityRepository.findAll();
    }
}
