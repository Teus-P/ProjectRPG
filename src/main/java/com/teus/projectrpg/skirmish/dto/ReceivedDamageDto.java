package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.skirmish.type.DamageMode;
import lombok.Data;

@Data
public class ReceivedDamageDto {

    private DamageMode mode = DamageMode.MANUAL;

    /**
     * In MANUAL mode the damage to apply. In ATTACK mode an optional override of the calculated damage.
     */
    private Integer damage;
    private long characterId;
    private BodyLocalizationType bodyLocalization;
    private Boolean isWeaponUndamaging;
    private Boolean isLosingTest;
    private int shield;

    /**
     * The target's shield used to parry. When set, its Shield value replaces {@code shield}.
     */
    private Long shieldWeaponId;
    private Boolean isSuddenDeath;
    private int destroyArmorValue;
    private Boolean isIgnoringArmor;

    // ATTACK mode only
    private Long attackerId;
    private Long weaponId;
    private Integer attackRoll;
    private int successPoints;
    private Boolean isCharging;
}
