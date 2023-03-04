package com.teus.projectrpg.injury;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.injury.service.InjuryService;
import com.teus.projectrpg.injury.type.InjuryType;
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
