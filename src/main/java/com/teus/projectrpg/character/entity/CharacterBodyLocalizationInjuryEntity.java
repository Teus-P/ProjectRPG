package com.teus.projectrpg.character.entity;

import com.teus.projectrpg.injury.entity.InjuryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_body_localization_injury")
public class CharacterBodyLocalizationInjuryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_body_localization_id")
    private CharacterBodyLocalizationEntity characterBodyLocalization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "injury_id")
    private InjuryEntity injury;

    @Column(name = "value")
    private int value;
}
