package com.teus.projectrpg.entity.character;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.weapon.WeaponEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "character_entity")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "is_right_handed")
    private Boolean isRightHanded;

    @JsonIgnore
    @OneToMany(mappedBy = "character", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CharacterCharacteristicEntity> characteristics = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "character", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CharacterSkillEntity> skills = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "character", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CharacterTalentEntity> talents = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "character_weapon",
    joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "weapon_id"))
    private List<WeaponEntity> weapons = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "character_armor",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "armor_id"))
    private List<ArmorEntity> armors = new ArrayList<>();
}