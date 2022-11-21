package com.teus.projectrpg.entity.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.entity.injury.CharacterBodyLocalizationInjuryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "character_body_localization")
public class CharacterBodyLocalizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity character;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "body_localization_id", nullable = false)
    private BodyLocalizationEntity bodyLocalization;

    @Column(name = "armor_points")
    private int armorPoints;

    @JsonIgnore
    @OneToMany(
            mappedBy = "characterBodyLocalization",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterBodyLocalizationInjuryEntity> injuries = new ArrayList<>();

    public void addInjury(CharacterBodyLocalizationInjuryEntity injury) {
        this.injuries.add(injury);
    }
}
