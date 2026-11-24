package com.teus.projectrpg.skirmish.type;

public enum AttackTestOutcome {
    /** A ranged attack that succeeded, or a melee attacker that won the opposed test (also with negative Success Levels). */
    HIT,
    /** A ranged attack that failed. In melee the attacker never "fails" alone, see {@link #DEFENDED}. */
    ATTACKER_FAILED,
    /** The defender won the opposed test in melee. */
    DEFENDED,
    /** A melee tie that could not be repeated for the players, because a roll was entered by hand. The test is to be rolled again. */
    TIE
}
