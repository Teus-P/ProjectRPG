package com.teus.projectrpg.services.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BaseService {

    <T, E extends BaseEntity> BaseDto<T, E> mapToDto(E entity);

}
