package com.teus.projectrpg.weapon.service.weapontype;

import com.teus.projectrpg.weapon.entity.WeaponTypeEntity;
import com.teus.projectrpg.weapon.repository.WeaponTypeRepository;
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
