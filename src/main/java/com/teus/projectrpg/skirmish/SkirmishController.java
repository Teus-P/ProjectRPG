package com.teus.projectrpg.skirmish;

import com.teus.projectrpg.character.dto.SkirmishCharacterDto;
import com.teus.projectrpg.skirmish.dto.AddConditionsDto;
import com.teus.projectrpg.skirmish.dto.EndTurnCheckDto;
import com.teus.projectrpg.skirmish.dto.ReceivedDamageDto;
import com.teus.projectrpg.skirmish.dto.SkirmishGroupDto;
import com.teus.projectrpg.skirmish.service.SkirmishGroupService;
import com.teus.projectrpg.skirmish.service.SkirmishService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SkirmishController {

    private final SkirmishService skirmishService;
    private final SkirmishGroupService skirmishGroupService;

    @PostMapping("/endTurnCheck")
    public EndTurnCheckDto endTurnCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        this.skirmishService.endTurnCheck(endTurnCheck);
        return endTurnCheck;
    }

    @PostMapping("/endTurnTestsCheck")
    public EndTurnCheckDto endTurnTestsCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        return this.skirmishService.endTurnCheckAfterTests(endTurnCheck);
    }

    @PostMapping("/receiveDamage")
    public void receiveDamage(@RequestBody ReceivedDamageDto receivedDamage) {
        this.skirmishService.receiveDamage(receivedDamage);
    }

    @PostMapping("/addWoundPoint")
    public void addWoundPoint(@RequestBody Long skirmishCharacterId) {
        this.skirmishService.addWoundPoint(skirmishCharacterId);
    }

    @PostMapping("/removeWoundPoint")
    public void removeWoundPoint(@RequestBody Long skirmishCharacterId) {
        this.skirmishService.removeWoundPoint(skirmishCharacterId);
    }

    @PostMapping("/addAdvantagePoint")
    public void addAdvantagePoint(@RequestBody Long skirmishCharacterId) {
        this.skirmishService.addAdvantagePoint(skirmishCharacterId);
    }

    @PostMapping("/removeAdvantagePoint")
    public void removeAdvantagePoint(@RequestBody Long skirmishCharacterId) {
        this.skirmishService.removeAdvantagePoint(skirmishCharacterId);
    }

    @PostMapping("/addGroupAdvantagePoint")
    public void addGroupAdvantagePoint(@RequestBody Long groupId) {
        this.skirmishGroupService.addGroupAdvantagePoint(groupId);
    }

    @PostMapping("/removeGroupAdvantagePoint")
    public void removeGroupAdvantagePoint(@RequestBody Long groupId) {
        this.skirmishGroupService.removeGroupAdvantagePoint(groupId);
    }

    @PostMapping("/addConditions")
    public ResponseEntity<List<SkirmishCharacterDto>> putSkirmishCharacters(@Valid @RequestBody AddConditionsDto addConditions) {
        return ResponseEntity.ok(this.skirmishService.addConditions(addConditions));
    }

    @PostMapping("/changeIsDeadValue")
    public void changeIsDeadValue(@Valid @RequestBody Map<String, Object> payload) {
        int skirmishCharacterId = (Integer) payload.get("id");
        Boolean value = (Boolean) payload.get("value");
        this.skirmishService.changeIsDeadValue((long) skirmishCharacterId, value);
    }

    @GetMapping("/skirmishGroups")
    public ResponseEntity<List<SkirmishGroupDto>> getSkirmishGroups() {
        List<SkirmishGroupDto> skirmishGroupDtos = skirmishGroupService.findAll();
        if (skirmishGroupDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(skirmishGroupDtos);
    }

    @PostMapping("/skirmishGroups")
    public ResponseEntity<SkirmishGroupDto> createSkirmishGroup(@Valid @RequestBody SkirmishGroupDto skirmishGroupDto) {
        return ResponseEntity.ok(skirmishGroupService.save(skirmishGroupDto));
    }

    @DeleteMapping("/skirmishGroups")
    public ResponseEntity<Void> deleteAllSkirmishGroups() {
        try {
            skirmishGroupService.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
