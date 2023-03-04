package com.teus.projectrpg.weapon.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.weapon.type.WeaponReachType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_reach")
public class WeaponReachEntity extends BaseEntity<WeaponReachType> {

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponReachType name;
}