package com.teus.projectrpg.character.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "character_armor")
public class CharacterArmorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "armor_id", nullable = false)
    private ArmorEntity armor;

    @JsonIgnore
    @OneToMany(
            mappedBy = "characterArmor",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CharacterArmorBodyLocalizationEntity> armorBodyLocalizations = new ArrayList<>();

    @Column(name = "duration")
    private Integer duration;
}
