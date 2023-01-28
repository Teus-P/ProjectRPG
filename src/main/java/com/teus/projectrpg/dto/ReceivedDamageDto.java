package com.teus.projectrpg.dto;

import com.teus.projectrpg.type.armor.BodyLocalizationType;
import lombok.Data;

@Data
public class ReceivedDamageDto {

    private int damage;
    private long characterId;
    private BodyLocalizationType bodyLocalization;
    private Boolean isWeaponUndamaging;
    private Boolean isLosingTest;
}
