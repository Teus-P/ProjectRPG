package com.teus.projectrpg.service.armor.armorpenalty;

import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.repository.armor.ArmorPenaltyRepository;
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
