package com.teus.projectrpg.character.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teus.projectrpg.armor.entity.ArmorEntity;
import com.teus.projectrpg.spell.entity.SpellEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "character_entity")
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

	@Column(name = "type")
	private String type;

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterCharacteristicEntity> characteristics = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterSkillEntity> skills = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterTalentEntity> talents = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterCreatureTraitEntity> traits = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "character_spell", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "spell_id"))
	private List<SpellEntity> spells = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterWeaponEntity> weapons = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "character_armor", joinColumns = @JoinColumn(name = "character_id"), inverseJoinColumns = @JoinColumn(name = "armor_id"))
	private List<ArmorEntity> armors = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterBodyLocalizationEntity> bodyLocalizations = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<CharacterConditionEntity> conditions = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "character", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	private List<NoteEntity> notes = new ArrayList<>();

}
