package com.teus.projectrpg.skirmish.service;

import com.teus.projectrpg.armor.entity.CharacterArmorBodyLocalizationEntity;
import com.teus.projectrpg.armor.type.ArmorType;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.character.entity.*;
import com.teus.projectrpg.character.mapper.CharacterContext;
import com.teus.projectrpg.character.mapper.SkirmishCharacterMapper;
import com.teus.projectrpg.character.service.SkirmishCharacterService;
import com.teus.projectrpg.characteristic.service.CharacteristicService;
import com.teus.projectrpg.characteristic.type.CharacteristicType;
import com.teus.projectrpg.condition.dto.ConditionDto;
import com.teus.projectrpg.condition.service.ConditionService;
import com.teus.projectrpg.condition.type.ConditionType;
import com.teus.projectrpg.skill.service.SkillService;
import com.teus.projectrpg.skill.type.SkillType;
import com.teus.projectrpg.skirmish.dto.AddConditionsDto;
import com.teus.projectrpg.skirmish.dto.AttackResultDto;
import com.teus.projectrpg.skirmish.dto.AttackTestRequestDto;
import com.teus.projectrpg.skirmish.dto.AttackTestResultDto;
import com.teus.projectrpg.skirmish.dto.DefenderOptionDto;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.dto.TestDto;
import com.teus.projectrpg.skirmish.type.DamageMode;
import com.teus.projectrpg.weapon.dto.WeaponDto;
import com.teus.projectrpg.weapon.entity.WeaponEntity;
import com.teus.projectrpg.weapon.mapper.WeaponMapper;
import com.teus.projectrpg.weapon.repository.WeaponRepository;
import com.teus.projectrpg.weapon.type.WeaponType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SkirmishServiceImpl implements SkirmishService {

    private final SkirmishCharacterService skirmishCharacterService;
    private final CharacteristicService characteristicService;
    private final SkillService skillService;
    private final ConditionService conditionService;
    private final SkirmishCharacterMapper skirmishCharacterMapper;
    private final CharacterContext characterContext;
    private final AttackCalculator attackCalculator;
    private final AttackTestResolver attackTestResolver;
    private final ShieldDamageService shieldDamageService;
    private final WeaponRepository weaponRepository;
    private final WeaponMapper weaponMapper;

    private static final String UNARMED = "UNARMED";

    private EndTurnCheckDto endTurnCheck = new EndTurnCheckDto();

    @Transactional
    public void endTurnCheck(EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;
        List<SkirmishCharacterEntity> skirmishCharacters = this.skirmishCharacterService.findAll();
        checkConditions(skirmishCharacters);
        checkDuration(skirmishCharacters);
    }

    private void checkDuration(List<SkirmishCharacterEntity> skirmishCharacters) {
        skirmishCharacters.forEach(skirmishCharacter -> {
            Iterator<CharacterArmorEntity> iterator = skirmishCharacter.getCharacter().getArmors().iterator();
            while (iterator.hasNext()) {
                CharacterArmorEntity characterArmorEntity = iterator.next();
                if (characterArmorEntity.getArmor().getArmorType().getName().equals(ArmorType.MAGICAL)) {
                    characterArmorEntity.setDuration(characterArmorEntity.getDuration() - 1);
                    if (characterArmorEntity.getDuration() <= 0) {
                        iterator.remove();
                        this.skirmishCharacterService.calculateArmorPoints(skirmishCharacter);
                    }
                }
            }
        });
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
        test.setCondition(new ConditionDto(conditionType));
        test.setFeasible(true);
        endTurnCheck.getTests().add(test);
    }

    @Transactional
    public EndTurnCheckDto endTurnCheckAfterTests(EndTurnCheckDto endTurnCheck) {
        this.endTurnCheck = endTurnCheck;

        List<SkirmishCharacterEntity> skirmishCharacters = new ArrayList<>();

        for (TestDto test : this.endTurnCheck.getTests()) {
            SkirmishCharacterEntity character =
                    skirmishCharacterService.findEntityById(test.getSkirmishCharacter().getId());

            if (!character.getIsDead()) {
                checkConditionAfterTests(test, character);
                updateOrAddSkirmishCharacter(skirmishCharacters, character);
            }
        }

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
        Optional<CharacterConditionEntity> condition = character.getConditionByType(test.getCondition().getName());
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

    @Transactional
    public AttackResultDto receiveDamage(ReceivedDamageDto receivedDamage) {
        AttackResultDto result = this.resolveDamage(receivedDamage);
        this.applyDamage(receivedDamage, result.getIgnoredArmorPoints());
        if (result.isDamagesShield()) {
            SkirmishCharacterEntity target = skirmishCharacterService.findEntityById(receivedDamage.getCharacterId());
            result.setShieldChange(shieldDamageService.apply(target, receivedDamage.getShieldWeaponId(),
                    result.getShieldPoints()));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public AttackResultDto previewDamage(ReceivedDamageDto receivedDamage) {
        return this.resolveDamage(receivedDamage);
    }

    /**
     * In ATTACK mode fills the request with values calculated from the weapon qualities.
     * Does not change any entity.
     */
    private AttackResultDto resolveDamage(ReceivedDamageDto request) {
        if (request.getShieldWeaponId() != null) {
            SkirmishCharacterEntity shieldOwner = skirmishCharacterService.findEntityById(request.getCharacterId());
            request.setShield(shieldDamageService.shieldValue(shieldOwner, request.getShieldWeaponId()));
        }
        if (request.getMode() != DamageMode.ATTACK) {
            AttackResultDto manual = new AttackResultDto();
            manual.setDamage(request.getDamage() == null ? 0 : request.getDamage());
            manual.setDamageOverridden(true);
            manual.setDestroyArmorValue(request.getDestroyArmorValue());
            if (request.getShieldWeaponId() != null && request.getDestroyArmorValue() > 0) {
                // the shield the target parried with takes the damage instead of the armor
                manual.setDamagesShield(true);
                manual.setShieldPoints(request.getDestroyArmorValue());
                manual.setDestroyArmorValue(0);
                request.setDestroyArmorValue(0);
                SkirmishCharacterEntity shieldOwner = skirmishCharacterService.findEntityById(request.getCharacterId());
                manual.setShieldChange(shieldDamageService.describe(shieldOwner, request.getShieldWeaponId(),
                        manual.getShieldPoints()));
            }
            manual.setIsWeaponUndamaging(request.getIsWeaponUndamaging());
            manual.setIsIgnoringArmor(request.getIsIgnoringArmor());
            if (request.getBodyLocalization() != null) {
                SkirmishCharacterEntity target = skirmishCharacterService.findEntityById(request.getCharacterId());
                AttackCalculator.Mitigation mitigation = attackCalculator.mitigate(manual.getDamage(), target, request,
                        0, Boolean.TRUE.equals(request.getIsIgnoringArmor()),
                        Boolean.TRUE.equals(request.getIsWeaponUndamaging()));
                manual.setToughnessBonus(mitigation.toughnessBonus());
                manual.setArmorReduction(mitigation.armorReduction());
                manual.setWoundsLost(mitigation.woundsLost());
            }
            return manual;
        }
        if (request.getAttackerId() == null || request.getWeaponId() == null || request.getBodyLocalization() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "attackerId, weaponId and bodyLocalization are required in ATTACK mode");
        }
        SkirmishCharacterEntity attacker = skirmishCharacterService.findEntityById(request.getAttackerId());
        SkirmishCharacterEntity target = skirmishCharacterService.findEntityById(request.getCharacterId());
        WeaponEntity weapon = this.findAttackWeapon(attacker, request.getWeaponId());

        AttackResultDto result = attackCalculator.calculate(attacker, weapon, target, request);
        if (result.isDamagesShield()) {
            result.setShieldChange(shieldDamageService.describe(target, request.getShieldWeaponId(),
                    result.getShieldPoints()));
        }
        request.setDamage(result.getDamage());
        request.setDestroyArmorValue(result.getDestroyArmorValue());
        request.setIsWeaponUndamaging(result.getIsWeaponUndamaging());
        request.setIsIgnoringArmor(result.getIsIgnoringArmor());
        if (request.getIsSuddenDeath() == null) {
            request.setIsSuddenDeath(true);
        }
        if (request.getIsLosingTest() == null) {
            request.setIsLosingTest(true);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<WeaponDto> findAttackWeapons(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(skirmishCharacterId);
        return weaponMapper.toDtos(this.listAttackWeapons(character));
    }

    /**
     * The attacker may use any weapon from the equipment, and always the default unarmed attack
     * (unless the equipment already has its own). The strongest one comes first.
     */
    private List<WeaponEntity> listAttackWeapons(SkirmishCharacterEntity attacker) {
        List<WeaponEntity> weapons = new ArrayList<>(attacker.getCharacter().getWeapons().stream()
                .map(CharacterWeaponEntity::getWeapon)
                .toList());
        if (weapons.stream().noneMatch(weapon -> UNARMED.equals(weapon.getName()))) {
            Optional.ofNullable(weaponRepository.findWeaponEntityByName(UNARMED)).ifPresent(weapons::add);
        }
        // the strongest weapon first, so it is the one chosen by default; weapons that tie keep the order of the equipment
        weapons.sort(Comparator.comparingInt((WeaponEntity weapon) -> attackCalculator.baseDamage(attacker, weapon)).reversed());
        return weapons;
    }

    /**
     * Rolls (or takes the entered rolls of) the attack test: the attacker against the skill of the weapon, and in melee
     * the target against the better of its defences. Does not change any entity.
     */
    @Override
    @Transactional(readOnly = true)
    public AttackTestResultDto resolveAttackTest(AttackTestRequestDto request) {
        if (request.getAttackerId() == null || request.getTargetId() == null || request.getWeaponId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "attackerId, targetId and weaponId are required");
        }
        SkirmishCharacterEntity attacker = skirmishCharacterService.findEntityById(request.getAttackerId());
        SkirmishCharacterEntity target = skirmishCharacterService.findEntityById(request.getTargetId());
        WeaponEntity weapon = this.findAttackWeapon(attacker, request.getWeaponId());
        boolean isMelee = weapon.getWeaponType().getName() == WeaponType.MELEE;

        CharacteristicType attackCharacteristic = isMelee ? CharacteristicType.WEAPON_SKILL : CharacteristicType.BALLISTIC_SKILL;
        AttackTestResolver.Participant attackerSide = this.participant(attacker,
                AttackTestResolver.skillFor(weapon), attackCharacteristic,
                request.getAttackerModifier(), request.getAttackerRoll());
        if (!isMelee) {
            return attackTestResolver.resolveRanged(attackerSide);
        }

        List<DefenderOptionDto> options = this.defenderOptions(target);
        DefenderOptionDto chosen = options.stream()
                .filter(option -> request.getDefenderSkill() == null || option.skillType() == request.getDefenderSkill())
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The target cannot defend with " + request.getDefenderSkill()));
        AttackTestResolver.Participant defenderSide = new AttackTestResolver.Participant(
                chosen.characteristicType() == null ? chosen.skillType() : null,
                chosen.characteristicType(), chosen.value(), target.getAdvantage(),
                request.getDefenderModifier(), request.getDefenderRoll());
        return attackTestResolver.resolveMelee(attackerSide, defenderSide, options);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DefenderOptionDto> findDefenderOptions(Long skirmishCharacterId) {
        return this.defenderOptions(skirmishCharacterService.findEntityById(skirmishCharacterId));
    }

    /**
     * Dodge and the melee skill of every weapon the target carries (the fists when it carries none), best first.
     */
    private List<DefenderOptionDto> defenderOptions(SkirmishCharacterEntity target) {
        Map<SkillType, DefenderOptionDto> options = new LinkedHashMap<>();
        this.addOption(options, target, SkillType.DODGE, CharacteristicType.AGILITY);

        List<WeaponEntity> meleeWeapons = target.getCharacter().getWeapons().stream()
                .map(CharacterWeaponEntity::getWeapon)
                .filter(weapon -> weapon.getWeaponType().getName() == WeaponType.MELEE)
                .toList();
        meleeWeapons.forEach(weapon -> this.addOption(options, target,
                Optional.ofNullable(AttackTestResolver.skillFor(weapon)).orElse(SkillType.MELEE_BASIC),
                CharacteristicType.WEAPON_SKILL));
        if (meleeWeapons.isEmpty()) {
            this.addOption(options, target, SkillType.MELEE_BRAWLING, CharacteristicType.WEAPON_SKILL);
        }
        return options.values().stream()
                .sorted(Comparator.comparingInt(DefenderOptionDto::value).reversed())
                .toList();
    }

    private void addOption(Map<SkillType, DefenderOptionDto> options, SkirmishCharacterEntity character,
                           SkillType skillType, CharacteristicType fallback) {
        AttackTestResolver.Participant side = this.participant(character, skillType, fallback, 0, null);
        options.putIfAbsent(skillType, new DefenderOptionDto(skillType, side.characteristicType(), side.skillValue()));
    }

    /**
     * The character's skill, or its characteristic when it does not have the skill (an NPC usually has only the latter).
     */
    private AttackTestResolver.Participant participant(SkirmishCharacterEntity character, SkillType skillType,
                                                       CharacteristicType fallback, int modifier, Integer roll) {
        Optional<CharacterSkillEntity> skill = skillType == null ? Optional.empty()
                : skillService.getSkillByType(character.getCharacter().getSkills(), skillType);
        if (skill.isPresent()) {
            return new AttackTestResolver.Participant(skillType, null, skill.get().getValue(),
                    character.getAdvantage(), modifier, roll);
        }
        return new AttackTestResolver.Participant(null, fallback, character.getCharacteristicValueByType(fallback),
                character.getAdvantage(), modifier, roll);
    }

    private WeaponEntity findAttackWeapon(SkirmishCharacterEntity attacker, Long weaponId) {
        return this.listAttackWeapons(attacker).stream()
                .filter(weapon -> weapon.getId().equals(weaponId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The attacker cannot use weapon " + weaponId));
    }

    private void applyDamage(ReceivedDamageDto receivedDamage, int ignoredArmorPoints) {
        int finalDamage;
        int damage = receivedDamage.getDamage() == null ? 0 : receivedDamage.getDamage();
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(receivedDamage.getCharacterId());
        boolean isUndamaging = Boolean.TRUE.equals(receivedDamage.getIsWeaponUndamaging());
        finalDamage = attackCalculator.mitigate(damage, character, receivedDamage, ignoredArmorPoints,
                Boolean.TRUE.equals(receivedDamage.getIsIgnoringArmor()), isUndamaging).finalDamage();

        if (isUndamaging) {
            if (finalDamage > 0) {
                character.setCurrentWounds(character.getCurrentWounds() - finalDamage);
                character.setAdvantage(0);
            } else {
                if (receivedDamage.getIsLosingTest()) {
                    character.setAdvantage(0);
                }
            }
        } else {
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

        if (receivedDamage.getDestroyArmorValue() > 0) {
            destroyArmor(receivedDamage, character);
        }
    }

    private void destroyArmor(ReceivedDamageDto receivedDamage, SkirmishCharacterEntity character) {
        BodyLocalizationType targetBodyLocalization = receivedDamage.getBodyLocalization();

        List<CharacterArmorEntity> matchingArmors = character.getCharacter().getArmors().stream()
                .filter(armor -> armor.getArmorBodyLocalizations().stream()
                        .anyMatch(localization -> localization.getBodyLocalization().getName().equals(receivedDamage.getBodyLocalization())))
                .filter(armor -> !armor.getArmor().getArmorType().getName().equals(ArmorType.MAGICAL))
                .toList();

        List<CharacterArmorEntity> sortedArmors = matchingArmors.stream()
                .sorted(Comparator.comparingInt((CharacterArmorEntity armor) -> armor.getArmor().getLayer()).reversed())
                .toList();

        Optional<CharacterArmorBodyLocalizationEntity> bodyLocalizationToUpdate = sortedArmors.stream()
                .flatMap(armor -> armor.getArmorBodyLocalizations().stream())
                .filter(localization -> localization.getBodyLocalization().getName().equals(targetBodyLocalization))
                .filter(localization -> localization.getArmorPoints() > 0)
                .findFirst();

        if (bodyLocalizationToUpdate.isPresent()) {
            int newArmorPoints = bodyLocalizationToUpdate.get().getArmorPoints() - receivedDamage.getDestroyArmorValue();
            bodyLocalizationToUpdate.get().setArmorPoints(Math.max(newArmorPoints, 0));
        }

        recalculateArmor(character);
    }

    private void recalculateArmor(SkirmishCharacterEntity character) {
        character.getCharacter().getBodyLocalizations().forEach(localization -> {
            localization.setArmorPoints(0);
            character.getCharacter().getArmors().forEach(characterArmorEntity -> characterArmorEntity.getArmorBodyLocalizations().forEach(armorLocalization -> {
                if (armorLocalization.getBodyLocalization().getName().equals(localization.getBodyLocalization().getName())) {
                    localization.setArmorPoints(localization.getArmorPoints() + armorLocalization.getArmorPoints());
                }
            }));
        });
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

    public void addWoundPoint(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(skirmishCharacterId);
        int newWounds = character.getCurrentWounds() + 1;

        if (character.getCurrentWounds() == 0) {
            character.removeConditionByType(ConditionType.PRONE);
            character.removeConditionByType(ConditionType.UNCONSCIOUS);
        }

        character.setCurrentWounds(newWounds);

        skirmishCharacterService.saveDto(skirmishCharacterMapper.toDto(character, characterContext));
    }

    public void removeWoundPoint(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(skirmishCharacterId);
        int newWounds = character.getCurrentWounds() - 1;
        if (newWounds >= 0) {
            character.setCurrentWounds(newWounds);
            if (character.getCurrentWounds() == 0) {
                this.checkIfProne(character);
            }
            skirmishCharacterService.saveDto(skirmishCharacterMapper.toDto(character, characterContext));
        }
    }

    public void addAdvantagePoint(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(skirmishCharacterId);
        int newAdvantage = character.getAdvantage() + 1;
        character.setAdvantage(newAdvantage);

        skirmishCharacterService.saveDto(skirmishCharacterMapper.toDto(character, characterContext));
    }

    public void removeAdvantagePoint(Long skirmishCharacterId) {
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(skirmishCharacterId);
        int newAdvantage = character.getAdvantage() - 1;
        if (newAdvantage >= 0) {
            character.setAdvantage(newAdvantage);
            skirmishCharacterService.saveDto(skirmishCharacterMapper.toDto(character, characterContext));
        }
    }

    public int getBonusPoints(int value) {
        return (value / 10) % 100;
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

    @Override
    public void changeIsDeadValue(Long skirmishCharacterId, Boolean value) {
        SkirmishCharacterEntity character = skirmishCharacterService.findEntityById(skirmishCharacterId);
        character.setIsDead(value);
        skirmishCharacterService.saveDto(skirmishCharacterMapper.toDto(character, characterContext));
    }
}

