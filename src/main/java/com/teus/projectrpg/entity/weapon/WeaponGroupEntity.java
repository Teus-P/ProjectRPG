package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.type.weapon.WeaponGroupType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_group")
public class WeaponGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponGroupType name;
}