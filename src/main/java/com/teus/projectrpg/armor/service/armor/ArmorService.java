package com.teus.projectrpg.armor.service.armor;

import com.teus.projectrpg.armor.entity.ArmorEntity;

import java.util.List;

public interface ArmorService {

    List<ArmorEntity> findAll();

    ArmorEntity save(ArmorEntity armorEntity);

    void deleteById(Long id);

    ArmorEntity findByName(String name);
}
