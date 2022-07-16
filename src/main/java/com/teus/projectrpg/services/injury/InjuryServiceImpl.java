package com.teus.projectrpg.services.injury;

import com.teus.projectrpg.entity.injury.InjuryEntity;
import com.teus.projectrpg.repository.injury.InjuryRepository;
import com.teus.projectrpg.type.injury.InjuryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InjuryServiceImpl implements InjuryService{

    private final InjuryRepository injuryRepository;

    @Autowired
    public InjuryServiceImpl(InjuryRepository injuryRepository) {
        this.injuryRepository = injuryRepository;
    }

    @Override
    public List<InjuryEntity> findAll() {
        return injuryRepository.findAll();
    }

    @Override
    public InjuryEntity findByName(InjuryType injuryType) {
        return injuryRepository.findInjuryByName(injuryType);
    }
}
