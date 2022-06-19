package com.teus.projectrpg.services.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {

    @Override
    public <T, E extends BaseEntity> BaseDto<T, E> mapToDto(E entity) {
        BaseDto<T, E> baseDto = new BaseDto<>();
        baseDto.setId(entity.getId());
        baseDto.setName((T) entity.getName());

        return baseDto;
    }
}
