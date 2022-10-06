package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.EndTurnCheckDto;
import com.teus.projectrpg.services.skirmish.SkirmishService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkirmishController {

    private final SkirmishService skirmishService;

    public SkirmishController(SkirmishService skirmishService) {
        this.skirmishService = skirmishService;
    }

    @PostMapping("/endTurnCheck")
    EndTurnCheckDto endTurnCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        return this.skirmishService.endTurnCheck(endTurnCheck);
    }

    @PostMapping("/endTurnTestsCheck")
    EndTurnCheckDto endTurnTestsCheck(@RequestBody EndTurnCheckDto endTurnCheck) {
        return this.skirmishService.endTurnTestsCheck(endTurnCheck);
    }

}
