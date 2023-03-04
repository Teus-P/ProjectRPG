package com.teus.projectrpg.weapon.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.weapon.type.WeaponGroupType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_group")
public class WeaponGroupEntity extends BaseEntity<WeaponGroupType> {

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponGroupType name;
}