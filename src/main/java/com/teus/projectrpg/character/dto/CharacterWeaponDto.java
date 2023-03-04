package com.teus.projectrpg.character.dto;

import com.teus.projectrpg.weapon.dto.WeaponDto;
import lombok.Data;

@Data
public class CharacterWeaponDto {
    private Long id;
    private WeaponDto weapon;
    private int value;
}
