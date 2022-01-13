package com.teus.projectrpg.entity.weapon;

import com.teus.projectrpg.type.weapon.WeaponQualityType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "weapon_quality")
public class WeaponQualityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private WeaponQualityType name;

    @OneToMany(mappedBy = "weaponQuality")
    private List<WeaponQualityValueEntity> weaponQualities = new ArrayList<>();
}
