package com.teus.projectrpg.dto;

import lombok.Data;

@Data
public class CharacterWeaponDto {
    private Long id;
    private WeaponDto weapon;
    private int value;

    public CharacterWeaponDto() {
    }
}
