package com.teus.projectrpg.injury.service;

import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.injury.entity.InjuryEntity;
import com.teus.projectrpg.injury.repository.InjuryRepository;
import com.teus.projectrpg.injury.type.InjuryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InjuryServiceImpl implements InjuryService {

    private final InjuryRepository injuryRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<InjuryType>> findAll() {
        return baseMapper.toDtos(injuryRepository.findAll());
    }

    @Override
    public InjuryEntity findByName(InjuryType injuryType) {
        return injuryRepository.findInjuryByName(injuryType);
    }
}
