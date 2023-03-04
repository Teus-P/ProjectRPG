package com.teus.projectrpg.weapon.entity;

import com.teus.projectrpg.base.entity.BaseEntity;
import com.teus.projectrpg.weapon.type.WeaponQualityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_quality")
public class WeaponQualityEntity extends BaseEntity<WeaponQualityType> {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private WeaponQualityType name;
}
