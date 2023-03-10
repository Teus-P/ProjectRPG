package com.teus.projectrpg.talent;

import com.teus.projectrpg.talent.dto.TalentDto;
import com.teus.projectrpg.talent.service.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TalentController {

    private final TalentService talentService;

    @GetMapping("/talent")
    public ResponseEntity<List<TalentDto>> getAllTalents() {
        List<TalentDto> talentDtos = talentService.findAll();
        if (talentDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(talentDtos);
    }
}
