package com.teus.projectrpg.entity.character;

import com.teus.projectrpg.entity.creaturetrait.CreatureTraitEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "character_creature_trait")
public class CharacterCreatureTraitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creature_trait_id", nullable = false)
    private CreatureTraitEntity trait;

    @Column(name = "value")
    private String value;
}
