package com.teus.projectrpg.skirmish.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AttackResultDto {

    private int damage;
    private boolean damageOverridden;
    private int destroyArmorValue;
    private Boolean isWeaponUndamaging = false;
    private Boolean isIgnoringArmor = false;
    private int ignoredArmorPoints;
    private boolean damagesShield;
    private int shieldPoints;
    private ShieldChangeDto shieldChange;
    private int toughnessBonus;
    private int armorReduction;
    private int woundsLost;
    private boolean missingRoll;
    private List<BreakdownEntryDto> breakdown = new ArrayList<>();
    private List<AttackQualityDto> qualities = new ArrayList<>();
}
