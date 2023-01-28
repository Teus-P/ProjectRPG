package com.teus.projectrpg.service.weapon.weapontype;

import com.teus.projectrpg.entity.weapon.WeaponTypeEntity;
import com.teus.projectrpg.repository.weapon.WeaponTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponTypeServiceImpl implements WeaponTypeService {

    private final WeaponTypeRepository weaponTypeRepository;

    @Override
    public List<WeaponTypeEntity> findAll() {
        return weaponTypeRepository.findAll();
    }
}
