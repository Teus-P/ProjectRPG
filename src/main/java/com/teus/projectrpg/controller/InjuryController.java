package com.teus.projectrpg.controller;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.mapper.base.BaseMapper;
import com.teus.projectrpg.service.injury.InjuryService;
import com.teus.projectrpg.type.injury.InjuryType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InjuryController {

    private final InjuryService injuryService;
    private final BaseMapper baseMapper;

    @GetMapping("/injury")
    List<BaseDto<InjuryType>> getAllInjuries() {
        return baseMapper.toDtos(injuryService.findAll());
    }
}
