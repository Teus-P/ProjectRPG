package com.teus.projectrpg.services.bodylocalization;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.armor.BodyLocalizationEntity;
import com.teus.projectrpg.repository.bodylocalization.BodyLocalizationRepository;
import com.teus.projectrpg.type.armor.BodyLocalizationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyLocalizationServiceImpl implements BodyLocalizationService {

    private final BodyLocalizationRepository bodyLocalizationRepository;

    @Autowired
    public BodyLocalizationServiceImpl(BodyLocalizationRepository bodyLocalizationRepository) {
        this.bodyLocalizationRepository = bodyLocalizationRepository;
    }

    @Override
    public List<BodyLocalizationEntity> findAll() {
        return bodyLocalizationRepository.findAll();
    }

    @Override
    public void save(BodyLocalizationEntity bodyLocalizationEntity) {
        bodyLocalizationRepository.save(bodyLocalizationEntity);
    }

    @Override
    public BodyLocalizationEntity findByName(BodyLocalizationType bodyLocalizationType) {
        return bodyLocalizationRepository.findBodyLocalizationByName(bodyLocalizationType);
    }

    @Override
    public BodyLocalizationEntity mapToEntity(BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalizationDto) {
        BodyLocalizationEntity bodyLocalization = new BodyLocalizationEntity();
        bodyLocalization.setId(bodyLocalizationDto.getId());
        bodyLocalization.setName(bodyLocalizationDto.getName());
        return bodyLocalization;

    }

    @Override
    public BaseDto<BodyLocalizationType, BodyLocalizationEntity> maptoDto(BodyLocalizationEntity bodyLocalization) {
        BaseDto<BodyLocalizationType, BodyLocalizationEntity> bodyLocalizationDto = new BaseDto<>();
        bodyLocalizationDto.setId(bodyLocalization.getId());
        bodyLocalizationDto.setName(bodyLocalization.getName());

        return bodyLocalizationDto;
    }
}
