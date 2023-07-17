package com.teus.projectrpg.character.entity;

import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.condition.type.ConditionType;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skirmish_character_entity")
public class SkirmishCharacterEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id", nullable = false)
	private Long id;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
	@JoinColumn(name = "character_id", referencedColumnName = "id")
	private CharacterEntity character;

	@Column
	private int currentWounds;

	@Column
	private int skirmishInitiative;

	@Column
	private int advantage;

	@Column
	private Boolean isDead;

	@Column
	private int sequenceNumber;

	public Optional<CharacterConditionEntity> getConditionByType(ConditionType conditionType) {
		return this.character.getConditions().stream().filter(c -> c.getCondition().getName().equals(conditionType)).findFirst();
	}

	public void removeConditionByType(ConditionType conditionType) {
		this.character.getConditions().removeIf(condition -> condition.getCondition().getName().equals(conditionType));
	}

	public int getCharacteristicValueByType(CharacteristicType characteristicType) {
		return this.character.getCharacteristics()
				.stream()
				.filter(c -> c.getCharacteristic().getName().equals(characteristicType))
				.findFirst()
				.get()
				.getValue();
	}

	public int getArmorForLocalization(BodyLocalizationType localization) {
		CharacterBodyLocalizationEntity bodyLocalization = this.character.getBodyLocalizations()
				.stream()
				.filter(c -> c.getBodyLocalization().getName().equals(localization))
				.findFirst()
				.get();
		return bodyLocalization.getArmorPoints() + bodyLocalization.getAdditionalArmorPoints();
	}

	public void addCondition(CharacterConditionEntity condition) {
		this.character.getConditions().add(condition);
	}

}
