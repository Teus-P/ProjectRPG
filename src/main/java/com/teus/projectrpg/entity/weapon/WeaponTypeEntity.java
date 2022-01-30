package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.weapon.WeaponType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_type")
public class WeaponTypeEntity extends BaseEntity<WeaponType> {

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponType name;
}