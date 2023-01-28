package com.teus.projectrpg.service.weapon.weaponreach;

import com.teus.projectrpg.entity.weapon.WeaponReachEntity;
import com.teus.projectrpg.repository.weapon.WeaponReachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeaponReachServiceImpl implements WeaponReachService {

    private final WeaponReachRepository weaponReachRepository;

    @Override
    public List<WeaponReachEntity> findAll() {
        return weaponReachRepository.findAll();
    }
}
