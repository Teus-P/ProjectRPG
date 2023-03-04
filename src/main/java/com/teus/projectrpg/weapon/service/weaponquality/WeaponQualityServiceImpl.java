package com.teus.projectrpg.weapon.service.weaponquality;

import com.teus.projectrpg.weapon.entity.WeaponQualityEntity;
import com.teus.projectrpg.weapon.repository.WeaponQualityRepository;
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
