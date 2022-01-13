package com.teus.projectrpg.services.armorservices.armorpenalty;

import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.repository.armor.ArmorPenaltyRepository;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorPenaltyServiceImpl implements ArmorPenaltyService{

    private final ArmorPenaltyRepository armorPenaltyRepository;

    @Autowired
    public ArmorPenaltyServiceImpl(ArmorPenaltyRepository armorPenaltyRepository) {
        this.armorPenaltyRepository = armorPenaltyRepository;
    }

    @Override
    public List<ArmorPenaltyEntity> findAll() {
        return armorPenaltyRepository.findAll();
    }

    @Override
    public void save(ArmorPenaltyEntity armorPenalty) {
        armorPenaltyRepository.save(armorPenalty);
    }

    @Override
    public ArmorPenaltyEntity findByType(ArmorPenaltyType armorPenaltyType) {
        return armorPenaltyRepository.findArmorPenaltyByName(armorPenaltyType);
    }
}
