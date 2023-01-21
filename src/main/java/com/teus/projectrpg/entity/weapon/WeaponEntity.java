package com.teus.projectrpg.entity.weapon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "weapon")
public class WeaponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "name_translation", nullable = false, unique = true)
    private String nameTranslation;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "weapon_type_id", nullable = false)
    private WeaponTypeEntity weaponType;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "weapon_group_id", nullable = false)
    private WeaponGroupEntity weaponGroup;

    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "weapon_reach_id", nullable = false)
    private WeaponReachEntity weaponReach;

    @Column(name = "weapon_range")
    private float weaponRange;

    @Column(name = "is_using_strength")
    private Boolean isUsingStrength;

    @Column(name = "is_using_strength_in_range")
    private Boolean isUsingStrengthInRange;

    @Column(name = "damage")
    private int damage;

    @JsonIgnore
    @OneToMany(
            mappedBy = "weapon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<WeaponQualityValueEntity> weaponQualities = new ArrayList<>();

}
