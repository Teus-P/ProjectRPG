package com.teus.projectrpg.character.dto;

import lombok.Data;

@Data
public class SkirmishCharacterDto {

	private Long id;
	private CharacterDto character;
	private int currentWounds;
	private int skirmishInitiative;
	private int advantage;
	private Boolean isDead;
	private int sequenceNumber;

}
