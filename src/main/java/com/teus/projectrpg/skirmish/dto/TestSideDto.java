package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.skill.type.SkillType;

/**
 * One side of the test. {@code skillType} is empty when the character has no such skill and its
 * {@code characteristicType} was used instead.
 */
public record TestSideDto(SkillType skillType,
                          CharacteristicType characteristicType,
                          int skillValue,
                          int advantageBonus,
                          int modifier,
                          int target,
                          int roll,
                          boolean rolledAutomatically,
                          int successLevel,
                          boolean success) {
}
