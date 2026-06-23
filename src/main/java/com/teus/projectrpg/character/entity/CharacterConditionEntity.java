package com.teus.projectrpg.character.entity;

import com.teus.projectrpg.condition.entity.ConditionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "character_condition")
public class CharacterConditionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "character_id")
	private CharacterEntity character;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "condition_id")
	private ConditionEntity condition;

	@Column(name = "value")
	private int value;

	@Column(name = "counter")
	private int counter;

}
