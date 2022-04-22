package com.teus.projectrpg.entity.character;

import com.teus.projectrpg.entity.weapon.WeaponEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_weapon")
public class CharacterWeaponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weapon_id", nullable = false)
    private WeaponEntity weapon;

    @Column(name = "value")
    private int value;
}