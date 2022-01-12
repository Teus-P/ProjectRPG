package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.dto.WeaponTypeDto;
import com.teus.projectrpg.type.weapon.WeaponType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_type")
public class WeaponTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponType name;

    public WeaponTypeEntity() {

    }

    public WeaponTypeEntity(WeaponTypeDto weaponTypeDto) {
        this.id = weaponTypeDto.getId();
        this.name = weaponTypeDto.getWeaponType();
    }
}