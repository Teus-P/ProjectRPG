package com.teus.projectrpg.services.character;

import com.teus.projectrpg.entity.armor.ArmorBodyLocalizationEntity;
import com.teus.projectrpg.entity.armor.ArmorEntity;
import com.teus.projectrpg.entity.character.CharacterBodyLocalizationEntity;
import com.teus.projectrpg.entity.character.CharacterEntity;
import com.teus.projectrpg.repository.character.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<CharacterEntity> findAll() {
        return characterRepository.findAll();
    }

    @Override
    public CharacterEntity save(CharacterEntity characterEntity) {
        return characterRepository.save(characterEntity);
    }

    @Override
    public void deleteById(Long id) {
        characterRepository.deleteById(id);
    }

    @Override
    public int calculateArmorPointsForBodyLocalization(CharacterBodyLocalizationEntity bodyLocalization,  List<ArmorEntity> armors) {
        int armorPoints = 0;
        for(ArmorEntity armor : armors) {
            for(ArmorBodyLocalizationEntity armorBodyLocalization : armor.getArmorBodyLocalizations()) {
                if(armorBodyLocalization.getBodyLocalization().equals(bodyLocalization.getBodyLocalization())) {
                    armorPoints += armorBodyLocalization.getArmorPoints();
                }
            }
        }

        return armorPoints;
    }
}
