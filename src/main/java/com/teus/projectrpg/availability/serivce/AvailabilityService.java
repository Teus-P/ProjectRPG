package com.teus.projectrpg.availability.serivce;

import com.teus.projectrpg.availability.type.AvailabilityType;
import com.teus.projectrpg.base.dto.BaseDto;

import java.util.List;

public interface AvailabilityService {
    List<BaseDto<AvailabilityType>> findAll();
}
