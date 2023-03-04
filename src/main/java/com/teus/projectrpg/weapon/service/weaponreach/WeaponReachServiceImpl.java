package com.teus.projectrpg.weapon.service.weaponreach;

import com.teus.projectrpg.weapon.entity.WeaponReachEntity;
import com.teus.projectrpg.weapon.repository.WeaponReachRepository;
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
