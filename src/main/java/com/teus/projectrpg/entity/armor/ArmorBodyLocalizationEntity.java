package com.teus.projectrpg.entity.armor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "armor_body_localization")
public class ArmorBodyLocalizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "armor_id", nullable = false)
    private ArmorEntity armor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "body_localization_id", nullable = false)
    private BodyLocalizationEntity bodyLocalization;

    @Column(name = "armor_points")
    private int armorPoints;

    @Column(name = "broken_armor_points")
    private int brokenArmorPoints;
}
