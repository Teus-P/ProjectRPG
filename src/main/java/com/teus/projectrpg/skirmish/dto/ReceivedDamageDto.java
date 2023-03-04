package com.teus.projectrpg.skirmish.dto;

import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.Data;

@Data
public class ReceivedDamageDto {

    private int damage;
    private long characterId;
    private BodyLocalizationType bodyLocalization;
    private Boolean isWeaponUndamaging;
    private Boolean isLosingTest;
    private int shield;
}
