package com.teus.projectrpg.dto;

import com.teus.projectrpg.entity.character.CharacterWeaponEntity;
import lombok.Data;

@Data
public class CharacterWeaponDto {
    private Long id;
    private WeaponDto weapon;
    private int value;

    public CharacterWeaponDto() {
    }

    public CharacterWeaponDto(CharacterWeaponEntity characterWeaponEntity) {
        this.id = characterWeaponEntity.getId();
        this.weapon = new WeaponDto(characterWeaponEntity.getWeapon());
        this.value = characterWeaponEntity.getValue();
    }
}
