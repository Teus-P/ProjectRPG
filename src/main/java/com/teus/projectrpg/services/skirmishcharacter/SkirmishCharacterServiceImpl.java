package com.teus.projectrpg.services.skirmishcharacter;

import com.teus.projectrpg.entity.skirmishcharacter.SkirmishCharacterEntity;
import com.teus.projectrpg.repository.skirmishcharacter.SkirmishCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkirmishCharacterServiceImpl implements SkirmishCharacterService {

    private final SkirmishCharacterRepository skirmishCharacterRepository;

    @Autowired
    public SkirmishCharacterServiceImpl(SkirmishCharacterRepository skirmishCharacterRepository) {
        this.skirmishCharacterRepository = skirmishCharacterRepository;
    }

    @Override
    public List<SkirmishCharacterEntity> findAll() {
        return skirmishCharacterRepository.findAll();
    }

    @Override
    public SkirmishCharacterEntity save(SkirmishCharacterEntity skirmishCharacterEntity) {
        return skirmishCharacterRepository.save(skirmishCharacterEntity);
    }

    @Override
    public void deleteById(Long id) {
        skirmishCharacterRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        skirmishCharacterRepository.deleteAll();
    }
}
