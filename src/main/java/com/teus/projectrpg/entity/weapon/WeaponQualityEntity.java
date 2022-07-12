package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.entity.base.BaseEntity;
import com.teus.projectrpg.type.weapon.WeaponQualityType;
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
