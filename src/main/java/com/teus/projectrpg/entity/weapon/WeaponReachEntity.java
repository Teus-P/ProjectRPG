package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.weapon.WeaponReachType;
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