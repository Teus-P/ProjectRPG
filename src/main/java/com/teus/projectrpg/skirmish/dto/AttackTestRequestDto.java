package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.skill.type.SkillType;
import lombok.Data;

/**
 * A roll left empty is rolled by the application; a roll that is given (a player rolling real dice) is used as it is.
 */
@Data
public class AttackTestRequestDto {

    private Long attackerId;
    private Long targetId;
    private Long weaponId;
    private Integer attackerRoll;
    private Integer defenderRoll;
    private int attackerModifier;
    private int defenderModifier;
    /** The defender's skill in melee. Empty means the best of the available ones. */
    private SkillType defenderSkill;
}
