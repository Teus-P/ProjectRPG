package com.teus.projectrpg.entity.weapon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "weapon_quality_value_entity")
public class WeaponQualityValueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weapon_id", nullable = false)
    private WeaponEntity weaponEntity;

    @ManyToOne
    @JoinColumn(name = "weapon_quality_id", nullable = false)
    private WeaponQualityEntity weaponQualityEntity;

    @Column(name = "value")
    private int value;
}
