package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.character.dto.CharacterBodyLocalizationDto;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.character.entity.CharacterConditionEntity;
import com.teus.projectrpg.character.entity.CharacterEntity;
import com.teus.projectrpg.character.entity.CharacterSkillEntity;
import com.teus.projectrpg.character.entity.SkirmishCharacterEntity;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.SkirmishCharacterMapper;
import com.teus.projectrpg.character.service.CharacterService;
import com.teus.projectrpg.character.service.SkirmishCharacterService;
import com.teus.projectrpg.characteristic.service.CharacteristicService;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.service.ConditionService;
import com.teus.projectrpg.condition.type.ConditionType;
import com.teus.projectrpg.skill.service.SkillService;
import com.teus.projectrpg.skill.type.SkillType;
import com.teus.projectrpg.skirmish.dto.AddConditionsDto;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.dto.TestDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkirmishServiceImpl implements SkirmishService {

	private final SkirmishCharacterService skirmishCharacterService;
	private final CharacterService characterService;
	private final CharacteristicService characteristicService;
	private final SkillService skillService;
	private final ConditionService conditionService;
	private final SkirmishCharacterMapper skirmishCharacterMapper;
	private final CharacterContext characterContext;

	private EndTurnCheckDto endTurnCheck = new EndTurnCheckDto();

	public void endTurnCheck(EndTurnCheckDto endTurnCheck) {
		this.endTurnCheck = endTurnCheck;
		List<SkirmishCharacterEntity> skirmishCharacters = this.skirmishCharacterService.findAll();
		checkConditions(skirmishCharacters);

		this.skirmishCharacterService.saveAllDtos(skirmishCharacterMapper.toDtos(skirmishCharacters, characterContext));
	}

	private void checkConditions(List<SkirmishCharacterEntity> skirmishCharacters) {
		skirmishCharacters.forEach(skirmishCharacter -> {
			if (!skirmishCharacter.getCharacter().getConditions().isEmpty()) {
				this.checkUnconscious(skirmishCharacter.getCharacter().getConditions());
				if (!skirmishCharacter.getIsDead()) {
					ListIterator<CharacterConditionEntity> iterator = skirmishCharacter.getCharacter().getConditions().listIterator();
					while (iterator.hasNext()) {
						CharacterConditionEntity condition = iterator.next();
						if (!skirmishCharacter.getIsDead()) {
							switch (condition.getCondition().getName()) {
								case BLEEDING -> this.checkBleeding(condition, skirmishCharacter, iterator);
								case DEAFENED -> this.checkDeafened(condition, iterator);
								case STUNNED -> this.checkStunned(skirmishCharacter);
								case BLINDED -> this.checkBlinded(condition, iterator);
								case BROKEN -> this.checkBroken(skirmishCharacter);
								case ABLAZE -> this.checkAblaze(skirmishCharacter);
								case SURPRISED -> this.checkSurprised(iterator);
								case POISON -> this.checkPoison(condition, skirmishCharacter);
							}
						} else {
							break;
						}
					}
					this.checkIfProne(skirmishCharacter);
				}
			}
		});
	}

	private void checkBleeding(CharacterConditionEntity condition, SkirmishCharacterEntity character,
			ListIterator<CharacterConditionEntity> iterator) {
		if (character.getConditionByType(ConditionType.UNCONSCIOUS).isPresent() && character.getCurrentWounds() <= 0) {
			createTestForCondition(character, ConditionType.BLEEDING, 0);
		} else {
			setDamageFromBleeding(condition, character, iterator);
		}
	}

	private void setDamageFromBleeding(CharacterConditionEntity condition, SkirmishCharacterEntity skirmishCharacter,
			ListIterator<CharacterConditionEntity> iterator) {
		skirmishCharacter.setCurrentWounds(skirmishCharacter.getCurrentWounds() - condition.getValue());
		if (skirmishCharacter.getCurrentWounds() <= 0) {
			skirmishCharacter.setCurrentWounds(0);
			if (skirmishCharacter.getConditionByType(ConditionType.UNCONSCIOUS).isEmpty()) {
				CharacterConditionEntity newCondition = new CharacterConditionEntity();
				newCondition.setCharacter(skirmishCharacter.getCharacter());
				newCondition.setValue(1);
				int toughness = skirmishCharacter.getCharacteristicValueByType(CharacteristicType.TOUGHNESS);
				newCondition.setCounter((toughness % 100) / 10);
				newCondition.setCondition(conditionService.findByName(ConditionType.UNCONSCIOUS));
				iterator.add(newCondition);
			}
		}
	}

	private void checkDeafened(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
		condition.setValue(condition.getValue() - 1);
		if (condition.getValue() <= 0) {
			iterator.remove();
		}
	}

	private void checkStunned(SkirmishCharacterEntity skirmishCharacter) {
		createTestForCondition(skirmishCharacter, ConditionType.STUNNED, 0);
	}

	private void checkBlinded(CharacterConditionEntity condition, ListIterator<CharacterConditionEntity> iterator) {
		condition.setCounter(condition.getCounter() - 1);
		if (condition.getCounter() == 0 || condition.getCounter() % 2 == 0) {
			condition.setValue(condition.getValue() - 1);
			if (condition.getValue() <= 0) {
				iterator.remove();
			}
		}
	}

	private void checkBroken(SkirmishCharacterEntity character) {
		createTestForCondition(character, ConditionType.BROKEN, 0);
	}

	private void checkAblaze(SkirmishCharacterEntity character) {
		createTestForCondition(character, ConditionType.ABLAZE, 0);
	}

	private void checkSurprised(ListIterator<CharacterConditionEntity> iterator) {
		iterator.remove();
	}

	private void checkPoison(CharacterConditionEntity condition, SkirmishCharacterEntity character) {
		character.setCurrentWounds(character.getCurrentWounds() - condition.getValue());
		createTestForCondition(character, ConditionType.POISON, 0);
	}

	private void checkUnconscious(List<CharacterConditionEntity> conditions) {
		Optional<CharacterConditionEntity> unconscious = conditions.stream()
				.filter(c -> c.getCondition().getName().equals(ConditionType.UNCONSCIOUS))
				.findFirst();

		if (unconscious.isPresent()) {
			if (unconscious.get().getCounter() > 0) {
				unconscious.get().setCounter(unconscious.get().getCounter() - 1);
				if (unconscious.get().getCounter() <= 0) {
					unconscious.get().setValue(1);
					unconscious.get().setCounter(0);
				}
			}
		}
	}

	private void createTestForCondition(SkirmishCharacterEntity character, ConditionType conditionType, int modifier) {
		TestDto test = new TestDto();
		test.setSkirmishCharacter(skirmishCharacterMapper.toDto(character, characterContext));
		test.setModifier(modifier);
		test.setConditionType(new ConditionDto(conditionType));
		test.setFeasible(true);
		endTurnCheck.getTests().add(test);
	}

	public EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck) {
		this.endTurnCheck = endTurnCheck;
		List<SkirmishCharacterEntity> skirmishCharacters = new ArrayList<>();
		for (TestDto test : this.endTurnCheck.getTests()) {
			SkirmishCharacterEntity character = skirmishCharacterService.findById(test.getSkirmishCharacter().getId());
			if (!character.getIsDead()) {
				this.checkConditionAfterTests(test, character);
				this.updateOrAddSkirmishCharacter(skirmishCharacters, character);
			}
		}

		this.skirmishCharacterService.saveAllDtos(skirmishCharacterMapper.toDtos(skirmishCharacters, characterContext));
		return this.endTurnCheck;
	}

	private void updateOrAddSkirmishCharacter(List<SkirmishCharacterEntity> skirmishCharacters, SkirmishCharacterEntity character) {
		Optional<SkirmishCharacterEntity> existingCharacter = skirmishCharacters.stream()
				.filter(existing -> existing.getId().equals(character.getId()))
				.findFirst();

		existingCharacter.ifPresentOrElse(
				existing -> skirmishCharacters.set(skirmishCharacters.indexOf(existing), existing),
				() -> skirmishCharacters.add(character)
		);
	}

	private void checkConditionAfterTests(TestDto test, SkirmishCharacterEntity character) {
		Optional<CharacterConditionEntity> condition = character.getConditionByType(test.getConditionType().getName());
		if (condition.isPresent()) {
			switch (condition.get().getCondition().getName()) {
				case BLEEDING -> this.checkBleedingTest(test, condition.get(), character);
				case STUNNED -> this.checkStunnedTest(test, condition.get(), character);
				case BROKEN -> this.checkBrokenTest(test, condition.get(), character);
				case ABLAZE -> this.checkAblazeTest(test, condition.get(), character);
				case POISON -> this.checkPoisonTest(test, condition.get(), character);
			}
		}

	}

	private void checkBleedingTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character) {
		if ((test.getResult() / 10) % 100 == test.getResult() % 10) {
			condition.setValue(condition.getValue() - 1);
			if (condition.getValue() <= 0) {
				character.removeConditionByType(condition.getCondition().getName());
			}
		} else if (test.getResult() <= (condition.getValue() * 10)) {
			character.setIsDead(true);
		}
	}

	private void checkStunnedTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character) {
		Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getCharacter().getSkills(), SkillType.ENDURANCE);
		int skillValue = skill.map(CharacterSkillEntity::getValue)
				.orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacter().getCharacteristics(),
						CharacteristicType.TOUGHNESS));
		if (test.getResult() <= (skillValue + test.getModifier())) {
			addFatiguedAfterConditionRemoving(test, condition, character, skillValue);
		}
	}

	private void checkBrokenTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character) {
		if (test.isFeasible()) {
			Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getCharacter().getSkills(), SkillType.COOL);
			int skillValue = skill.map(CharacterSkillEntity::getValue)
					.orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacter().getCharacteristics(),
							CharacteristicType.WILLPOWER));
			if (test.getResult() > 0 && test.getResult() < 100 && test.getResult() <= (skillValue + test.getModifier())) {
				condition.setValue(condition.getValue() - (((skillValue / 10) % 100) - ((test.getResult() / 10) % 100)) - 1);
				if (condition.getValue() <= 0) {
					character.removeConditionByType(condition.getCondition().getName());
					if (character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
						CharacterConditionEntity fatigued = new CharacterConditionEntity();
						fatigued.setCondition(conditionService.findByName(ConditionType.FATIGUED));
						fatigued.setValue(1);
						fatigued.setCharacter(character.getCharacter());
						character.addCondition(fatigued);
					} else {
						CharacterConditionEntity fatigued = character.getConditionByType(ConditionType.FATIGUED).get();
						fatigued.setValue(fatigued.getValue() + 1);
					}
				}
			}
		}
	}

	private void checkAblazeTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character) {
		int damage = test.getResult() + condition.getValue();
		int toughnessBonus = getBonusPoints(character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS));
		int lowestArmor = character.getCharacter()
				.getBodyLocalizations()
				.stream()
				.min(Comparator.comparing(CharacterBodyLocalizationEntity::getArmorPoints))
				.orElseThrow(NoSuchElementException::new)
				.getArmorPoints();

		int finalDamage = damage - toughnessBonus - lowestArmor;
		finalDamage = Math.max(finalDamage, 1);

		character.setCurrentWounds(character.getCurrentWounds() - finalDamage);
		character.setAdvantage(0);

		if (character.getCurrentWounds() <= 0) {
			character.setIsDead(true);
			character.setCurrentWounds(0);
			character.removeConditionByType(ConditionType.ABLAZE);
		}
	}

	private void checkPoisonTest(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character) {
		Optional<CharacterSkillEntity> skill = skillService.getSkillByType(character.getCharacter().getSkills(), SkillType.ENDURANCE);
		int skillValue = skill.map(CharacterSkillEntity::getValue)
				.orElseGet(() -> characteristicService.getCharacteristicValueByType(character.getCharacter().getCharacteristics(),
						CharacteristicType.TOUGHNESS));

		boolean isTestSuccessful = test.getResult() <= (skillValue + test.getModifier());

		Optional<CharacterConditionEntity> unconscious = character.getConditionByType(ConditionType.UNCONSCIOUS);
		if (unconscious.isPresent() && unconscious.get().getValue() > 0) {
			if (!isTestSuccessful) {
				character.setIsDead(true);
			}
		} else if (isTestSuccessful) {
			addFatiguedAfterConditionRemoving(test, condition, character, skillValue);
		}
	}

	private void addFatiguedAfterConditionRemoving(TestDto test, CharacterConditionEntity condition, SkirmishCharacterEntity character,
			int skillValue) {
		condition.setValue(condition.getValue() - (getBonusPoints(skillValue) - getBonusPoints(test.getResult())) - 1);
		if (condition.getValue() <= 0) {
			character.removeConditionByType(condition.getCondition().getName());
			if (character.getConditionByType(ConditionType.FATIGUED).isEmpty()) {
				CharacterConditionEntity newCondition = new CharacterConditionEntity();
				newCondition.setCondition(conditionService.findByName(ConditionType.FATIGUED));
				newCondition.setValue(1);
				newCondition.setCharacter(character.getCharacter());
				character.addCondition(newCondition);
			}
		}
	}

	public void receiveDamage(ReceivedDamageDto receivedDamage) {
		int finalDamage;
		SkirmishCharacterEntity character = skirmishCharacterService.findById(receivedDamage.getCharacterId());
		int toughnessBonus = ((character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS) / 10) % 100);

		int armorForLocalization = 0;
		if (!receivedDamage.getIsIgnoringArmor()) {
			armorForLocalization = character.getArmorForLocalization(receivedDamage.getBodyLocalization()) + receivedDamage.getShield();
		}

		if (receivedDamage.getIsWeaponUndamaging()) {
			armorForLocalization *= 2;
			finalDamage = receivedDamage.getDamage() - toughnessBonus - armorForLocalization;
			if (finalDamage > 0) {
				character.setCurrentWounds(character.getCurrentWounds() - finalDamage);
				character.setAdvantage(0);
			} else {
				if (receivedDamage.getIsLosingTest()) {
					character.setAdvantage(0);
				}
			}
		} else {
			finalDamage = receivedDamage.getDamage() - toughnessBonus - armorForLocalization;
			if (finalDamage >= 1) {
				character.setCurrentWounds(character.getCurrentWounds() - finalDamage);
			} else {
				character.setCurrentWounds(character.getCurrentWounds() - 1);
			}
			character.setAdvantage(0);
		}

		if (receivedDamage.getIsSuddenDeath()) {
			checkSuddenDeath(character);
		} else {
			this.checkIfProne(character);
		}

		if (character.getConditionByType(ConditionType.SURPRISED).isPresent()) {
			character.removeConditionByType(ConditionType.SURPRISED);
		}

		List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getCharacter().getBodyLocalizations();
		bodyLocalizations.stream()
				.filter(o -> o.getBodyLocalization().getName().equals(receivedDamage.getBodyLocalization()))
				.findFirst()
				.ifPresent(o -> {
					o.setAdditionalArmorPoints(o.getAdditionalArmorPoints() - receivedDamage.getDestroyArmorValue());
					if (Math.abs(o.getAdditionalArmorPoints()) > o.getArmorPoints()) {
						o.setAdditionalArmorPoints(-o.getArmorPoints());
					}
				});

		character.getCharacter().setBodyLocalizations(bodyLocalizations);

		skirmishCharacterService.save(skirmishCharacterMapper.toDto(character, characterContext));
	}

	private void checkIfProne(SkirmishCharacterEntity character) {
		if (character.getCurrentWounds() <= 0) {
			character.setCurrentWounds(0);
			if (character.getConditionByType(ConditionType.PRONE).isEmpty()) {
				CharacterConditionEntity prone = new CharacterConditionEntity();
				prone.setCondition(conditionService.findByName(ConditionType.PRONE));
				prone.setValue(1);
				prone.setCharacter(character.getCharacter());
				character.addCondition(prone);
			}

			if (character.getConditionByType(ConditionType.UNCONSCIOUS).isEmpty()) {
				CharacterConditionEntity unconscious = new CharacterConditionEntity();
				unconscious.setCondition(conditionService.findByName(ConditionType.UNCONSCIOUS));
				unconscious.setValue(0);
				unconscious.setCounter(getBonusPoints(character.getCharacteristicValueByType(CharacteristicType.TOUGHNESS)));
				unconscious.setCharacter(character.getCharacter());
				character.addCondition(unconscious);
			}
		}
	}

	private void checkSuddenDeath(SkirmishCharacterEntity character) {
		if (character.getCurrentWounds() <= 0) {
			character.setCurrentWounds(0);
			character.setIsDead(true);
		}
	}

	public void addAdvantagePoint(Long skirmishCharacterId) {
		SkirmishCharacterEntity character = skirmishCharacterService.findById(skirmishCharacterId);
		int newAdvantage = character.getAdvantage() + 1;
		character.setAdvantage(newAdvantage);

		skirmishCharacterService.save(skirmishCharacterMapper.toDto(character, characterContext));
	}

	public void removeAdvantagePoint(Long skirmishCharacterId) {
		SkirmishCharacterEntity character = skirmishCharacterService.findById(skirmishCharacterId);
		int newAdvantage = character.getAdvantage() - 1;
		if (newAdvantage >= 0) {
			character.setAdvantage(newAdvantage);
		}

		skirmishCharacterService.save(skirmishCharacterMapper.toDto(character, characterContext));
	}

	public int getBonusPoints(int value) {
		return (value / 10) % 100;
	}

	public void addAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization) {
		CharacterEntity character = characterService.findById(bodyLocalization.getCharacterId());
		List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getBodyLocalizations();
		bodyLocalizations.stream()
				.filter(o -> o.getBodyLocalization().getName().equals(bodyLocalization.getBodyLocalization().getName()))
				.findFirst()
				.ifPresent(o -> o.setAdditionalArmorPoints(o.getAdditionalArmorPoints() + 1));

		character.setBodyLocalizations(bodyLocalizations);
		characterService.saveEntity(character);
	}

	public void removeAdditionalArmorPoint(CharacterBodyLocalizationDto bodyLocalization) {
		CharacterEntity character = characterService.findById(bodyLocalization.getCharacterId());
		List<CharacterBodyLocalizationEntity> bodyLocalizations = character.getBodyLocalizations();
		bodyLocalizations.stream()
				.filter(o -> o.getBodyLocalization().getName().equals(bodyLocalization.getBodyLocalization().getName()))
				.findFirst()
				.ifPresent(o -> o.setAdditionalArmorPoints(o.getAdditionalArmorPoints() - 1));

		character.setBodyLocalizations(bodyLocalizations);
		characterService.saveEntity(character);
	}

	public List<SkirmishCharacterDto> addConditions(AddConditionsDto addConditions) {
		List<SkirmishCharacterEntity> characters = skirmishCharacterService.findAllById(addConditions.getCharactersIds());
		characters.forEach(character -> addConditions.getConditions().forEach(condition -> {
			Optional<CharacterConditionEntity> conditionByType = character.getConditionByType(condition.getCondition().getName());
			if (conditionByType.isEmpty()) {
				CharacterConditionEntity characterConditionEntity = new CharacterConditionEntity();
				characterConditionEntity.setCharacter(character.getCharacter());
				characterConditionEntity.setCondition(conditionService.findByName(condition.getCondition().getName()));
				characterConditionEntity.setValue(condition.getValue());
				characterConditionEntity.setCounter(condition.getCounter());
				character.addCondition(characterConditionEntity);
			} else {
				conditionByType.ifPresent(c -> {
					c.setValue(c.getValue() + condition.getValue());
					c.setCounter(c.getCounter() + condition.getCounter());
				});
			}
		}));

		return skirmishCharacterService.saveAllEntities(characters);
	}

}

