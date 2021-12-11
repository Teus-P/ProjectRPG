package com.teus.projectrpg.services.ArmorPenalty;

import com.teus.projectrpg.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.type.ArmorPenaltyType;

import java.util.List;

public interface ArmorPenaltyService {

    List<ArmorPenaltyEntity> findAll();

    void save(ArmorPenaltyEntity armorPenalty);

    ArmorPenaltyEntity findByType(ArmorPenaltyType armorPenaltyType);

}
