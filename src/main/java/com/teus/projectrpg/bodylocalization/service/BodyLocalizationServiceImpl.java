package com.teus.projectrpg.bodylocalization.service;

import com.teus.projectrpg.bodylocalization.entity.BodyLocalizationEntity;
import com.teus.projectrpg.base.dto.BaseDto;
import com.teus.projectrpg.base.mapper.BaseMapper;
import com.teus.projectrpg.bodylocalization.repository.BodyLocalizationRepository;
import com.teus.projectrpg.bodylocalization.type.BodyLocalizationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodyLocalizationServiceImpl implements BodyLocalizationService {

    private final BodyLocalizationRepository bodyLocalizationRepository;
    private final BaseMapper baseMapper;

    @Override
    public List<BaseDto<BodyLocalizationType>> findAll() {
        return baseMapper.toDtos(bodyLocalizationRepository.findAll());
    }

    @Override
    public void save(BodyLocalizationEntity bodyLocalizationEntity) {
        bodyLocalizationRepository.save(bodyLocalizationEntity);
    }

    @Override
    public BodyLocalizationEntity findByName(BodyLocalizationType bodyLocalizationType) {
        return bodyLocalizationRepository.findBodyLocalizationByName(bodyLocalizationType);
    }
}
