package com.teus.projectrpg.availability;

import com.teus.projectrpg.availability.serivce.AvailabilityService;
import com.teus.projectrpg.availability.type.AvailabilityType;
import com.teus.projectrpg.base.dto.BaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/availability")
    public ResponseEntity<List<BaseDto<AvailabilityType>>> getAllAvailabilities() {
        List<BaseDto<AvailabilityType>> all = availabilityService.findAll();
        if(all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

}
