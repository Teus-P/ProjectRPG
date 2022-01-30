package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
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