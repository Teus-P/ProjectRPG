package com.teus.projectrpg.skirmish.type;

public enum QualityRelevance {
    /** The calculator already used the quality in this attack. */
    APPLIED,
    /** The quality matters in this attack, but the player has to resolve it. */
    REMINDER,
    NONE
}
