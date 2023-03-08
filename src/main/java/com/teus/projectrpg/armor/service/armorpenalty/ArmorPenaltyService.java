package com.teus.projectrpg.armor.service.armorpenalty;

import com.teus.projectrpg.armor.entity.ArmorPenaltyEntity;
import com.teus.projectrpg.armor.type.ArmorPenaltyType;
import com.teus.projectrpg.base.dto.BaseDto;

import java.util.List;

public interface ArmorPenaltyService {

    List<BaseDto<ArmorPenaltyType>> findAll();

    void save(ArmorPenaltyEntity armorPenalty);

}
