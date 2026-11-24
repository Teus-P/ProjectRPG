package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skill.type.SkillType;

/**
 * A way to defend. {@code skillType} always names the option; {@code characteristicType} is set only when the target
 * has no such skill and the characteristic stands in for it.
 */
public record DefenderOptionDto(SkillType skillType, CharacteristicType characteristicType, int value) {
}
