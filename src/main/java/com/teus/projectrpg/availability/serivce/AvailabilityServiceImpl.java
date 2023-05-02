package com.teus.projectrpg.availability.serivce;

import com.teus.projectrpg.availability.repository.AvailabilityRepository;
import com.teus.projectrpg.availability.type.AvailabilityType;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<AvailabilityType>> findAll() {
        return baseMapper.toDtos(availabilityRepository.findAll());
    }
}
