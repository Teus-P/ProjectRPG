package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.skirmish.type.AttackTestOutcome;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AttackTestResultDto {

    /** Melee is an opposed test, a ranged attack is a single test with no roll for the target. */
    private boolean opposed;
    private TestSideDto attacker;
    private TestSideDto defender;
    private List<DefenderOptionDto> defenderOptions = new ArrayList<>();
    private AttackTestOutcome outcome;
    /** How many times a melee tie was rolled again by the application. */
    private int repeatedTimes;
    /** The Success Levels that go to the damage: the difference of the two tests in melee. Zero on a miss. */
    private int damageSuccessLevel;
    /** From the reversed digits of the attacker's roll. */
    private BodyLocalizationType bodyLocalization;
}
