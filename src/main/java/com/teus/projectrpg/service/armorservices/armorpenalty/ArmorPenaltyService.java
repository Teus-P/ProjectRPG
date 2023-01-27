package com.teus.projectrpg.service.armorservices.armorpenalty;

import com.teus.projectrpg.entity.armor.ArmorPenaltyEntity;

import java.util.List;

public interface ArmorPenaltyService {

    List<ArmorPenaltyEntity> findAll();

    void save(ArmorPenaltyEntity armorPenalty);

}
