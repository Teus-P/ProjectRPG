package com.teus.projectrpg.entity.skirmishcharacter;

import com.teus.projectrpg.entity.character.CharacterEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "skirmish_character_entity")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class SkirmishCharacterEntity extends CharacterEntity {

    @Column
    private int currentWounds;

    @Column
    private int skirmishInitiative;

    @Column
    private int advantage;

    @Column
    private Boolean isDead;
}
