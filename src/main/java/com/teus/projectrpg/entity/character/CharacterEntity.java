package com.teus.projectrpg.entity.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.condition.CharacterConditionEntity;
import com.teus.projectrpg.entity.note.NoteEntity;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import com.teus.projectrpg.type.characteristic.CharacteristicType;
import com.teus.projectrpg.type.condition.ConditionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "character_entity")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "group_column")
    private String groupColumn;

    @Column(name = "is_right_handed")
    private Boolean isRightHanded;

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterCharacteristicEntity> characteristics = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterSkillEntity> skills = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterTalentEntity> talents = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterWeaponEntity> weapons = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "character_armor",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "armor_id"))
    private List<ArmorEntity> armors = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterBodyLocalizationEntity> bodyLocalizations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<CharacterConditionEntity> conditions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "character",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<NoteEntity> notes = new ArrayList<>();

    public Optional<CharacterConditionEntity> getConditionByType(ConditionType conditionType) {
        return this.conditions.stream()
                .filter(c -> c.getCondition().getName().equals(conditionType))
                .findFirst();
    }

    public void addCondition(CharacterConditionEntity condition) {
        this.conditions.add(condition);
    }

    public int getCharacteristicValueByType(CharacteristicType characteristicType) {
        return this.characteristics.stream()
                .filter(c -> c.getCharacteristic().getName().equals(characteristicType))
                .findFirst()
                .get().getValue();
    }

    public int getArmorForLocalization(BodyLocalizationType localization) {
        return this.bodyLocalizations.stream()
                .filter(c -> c.getBodyLocalization().getName().equals(localization))
                .findFirst()
                .get().getArmorPoints();
    }
}
