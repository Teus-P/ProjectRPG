package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.type.weapon.WeaponReachType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_reach")
public class WeaponReachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private WeaponReachType name;
}