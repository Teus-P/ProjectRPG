package com.teus.projectrpg.services.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {

    @Override
    public <T, E extends BaseEntity<T>> BaseDto<T, E> mapToDto(E entity) {
        BaseDto<T, E> baseDto = new BaseDto<>();
        baseDto.setId(entity.getId());
        baseDto.setName((T) entity.getName());

        return baseDto;
    }

    @Override
    public <T, E extends BaseEntity<T>> List<BaseDto<T, E>> getBaseDtosList(List<E> entitiesList) {
        List<BaseDto<T, E>> dtosList = new ArrayList<>();

        for (E entity : entitiesList) {
            dtosList.add(mapToDto(entity));
        }

        return dtosList;
    }


}
