package com.teus.projectrpg.entity.weapon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.type.weapon.WeaponGroupType;
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
    @Column(name = "weapon_group")
    private WeaponGroupType weaponGroup;

    @Column(name = "weapon_range")
    private String weaponRange;

    @Column(name = "is_using_strength")
    private Boolean isUsingStrength;

    @Column(name = "damage")
    private int damage;

    @JsonIgnore
    @OneToMany(mappedBy = "weaponEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<WeaponQualityValueEntity> weaponQualities = new ArrayList<>();
}
