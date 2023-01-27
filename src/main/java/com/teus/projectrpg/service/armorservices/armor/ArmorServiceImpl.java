package com.teus.projectrpg.service.armorservices.armor;

import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.repository.armor.ArmorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorServiceImpl implements ArmorService {

    private final ArmorRepository armorRepository;

    @Autowired
    public ArmorServiceImpl(ArmorRepository armorRepository) {
        this.armorRepository = armorRepository;
    }

    @Override
    public List<ArmorEntity> findAll() {
        return armorRepository.findAll();
    }

    @Override
    public ArmorEntity save(ArmorEntity armorEntity) {
        return armorRepository.save(armorEntity);
    }

    @Override
    public void deleteById(Long id) {
        armorRepository.deleteById(id);
    }

    @Override
    public ArmorEntity findByName(String name) {
        return armorRepository.findArmorEntityByName(name);
    }
}
