package com.teus.projectrpg.armor.service.armorpenalty;

import com.teus.projectrpg.armor.entity.ArmorPenaltyEntity;

import java.util.List;

public interface ArmorPenaltyService {

    List<ArmorPenaltyEntity> findAll();

    void save(ArmorPenaltyEntity armorPenalty);

}
