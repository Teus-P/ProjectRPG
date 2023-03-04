package com.teus.projectrpg.armor.service.armorpenalty;

import com.teus.projectrpg.armor.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.armor.repository.ArmorPenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorPenaltyServiceImpl implements ArmorPenaltyService {

    private final ArmorPenaltyRepository armorPenaltyRepository;

    @Override
    public List<ArmorPenaltyEntity> findAll() {
        return armorPenaltyRepository.findAll();
    }

    @Override
    public void save(ArmorPenaltyEntity armorPenalty) {
        armorPenaltyRepository.save(armorPenalty);
    }
}
