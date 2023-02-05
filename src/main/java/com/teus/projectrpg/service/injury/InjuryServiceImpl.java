package com.teus.projectrpg.service.injury;

import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.repository.injury.InjuryRepository;
import com.teus.projectrpg.type.injury.InjuryType;
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