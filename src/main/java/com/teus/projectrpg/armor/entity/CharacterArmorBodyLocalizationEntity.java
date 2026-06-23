package com.teus.projectrpg.armor.entity;

import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.character.entity.CharacterArmorEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_armor_body_localization")
public class CharacterArmorBodyLocalizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_armor_id", nullable = false)
    private CharacterArmorEntity characterArmor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "body_localization_id", nullable = false)
    private BodyLocalizationEntity bodyLocalization;

    @Column(name = "armor_points")
    private int armorPoints;
}
