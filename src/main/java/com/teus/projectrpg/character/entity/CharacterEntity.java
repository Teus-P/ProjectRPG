package com.teus.projectrpg.character.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import com.teus.projectrpg.condition.entity.CharacterConditionEntity;
import com.teus.projectrpg.spell.entity.SpellEntity;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.condition.type.ConditionType;
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
    private String group;

    @Column
    private String status;

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
    private List<CharacterCreatureTraitEntity> traits = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "character_spell",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "spell_id"))
    private List<SpellEntity> spells = new ArrayList<>();

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

    public void removeConditionByType(ConditionType conditionType) {
        conditions.removeIf(condition -> condition.getCondition().getName().equals(conditionType));
    }

    public int getCharacteristicValueByType(CharacteristicType characteristicType) {
        return this.characteristics.stream()
                .filter(c -> c.getCharacteristic().getName().equals(characteristicType))
                .findFirst()
                .get().getValue();
    }

    public int getArmorForLocalization(BodyLocalizationType localization) {
        CharacterBodyLocalizationEntity bodyLocalization = this.bodyLocalizations.stream()
                .filter(c -> c.getBodyLocalization().getName().equals(localization))
                .findFirst()
                .get();
        return bodyLocalization.getArmorPoints() + bodyLocalization.getAdditionalArmorPoints();
    }

    public void addCondition(CharacterConditionEntity condition) {
        this.conditions.add(condition);
    }
}
