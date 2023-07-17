package com.teus.projectrpg.character.entity;

import com.teus.projectrpg.condition.entity.ConditionEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
