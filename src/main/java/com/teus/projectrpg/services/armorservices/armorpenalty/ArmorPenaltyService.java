package com.teus.projectrpg.services.armorservices.armorpenalty;

import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;
import com.teus.projectrpg.type.armor.ArmorPenaltyType;

import java.util.List;

public interface ArmorPenaltyService {

    List<ArmorPenaltyEntity> findAll();

    void save(ArmorPenaltyEntity armorPenalty);

    ArmorPenaltyEntity findByType(ArmorPenaltyType armorPenaltyType);

}
