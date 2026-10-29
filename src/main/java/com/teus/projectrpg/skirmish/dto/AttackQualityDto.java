package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.skirmish.type.QualityRelevance;
import com.teus.projectrpg.weapon.type.WeaponQualityType;

public record AttackQualityDto(WeaponQualityType name, String value, QualityRelevance relevance) {
}
