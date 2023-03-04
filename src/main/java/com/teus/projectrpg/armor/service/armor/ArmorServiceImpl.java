package com.teus.projectrpg.armor.service.armor;

import com.teus.projectrpg.armor.entity.ArmorEntity;
import com.teus.projectrpg.armor.repository.ArmorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorServiceImpl implements ArmorService {

    private final ArmorRepository armorRepository;

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
