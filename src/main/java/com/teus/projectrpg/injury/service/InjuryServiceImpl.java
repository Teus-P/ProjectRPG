package com.teus.projectrpg.injury.service;

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

    @Override
    public List<InjuryEntity> findAll() {
        return injuryRepository.findAll();
    }

    @Override
    public InjuryEntity findByName(InjuryType injuryType) {
        return injuryRepository.findInjuryByName(injuryType);
    }
}
