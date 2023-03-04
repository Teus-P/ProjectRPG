package com.teus.projectrpg.weapon.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.weapon.type.WeaponType;
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