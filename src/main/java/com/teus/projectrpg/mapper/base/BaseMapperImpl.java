package com.teus.projectrpg.mapper.base;

import com.teus.projectrpg.dto.BaseDto;
import com.teus.projectrpg.entity.base.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseMapperImpl implements BaseMapper {

    @Override
    public <T, E extends BaseEntity<T>> BaseDto<T> toDto(E entity) {
        BaseDto<T> baseDto = new BaseDto<>();
        BeanUtils.copyProperties(entity, baseDto);
        return baseDto;
    }

    @Override
    public <T, E extends BaseEntity<T>> List<BaseDto<T>> toDtos(List<E> entitiesList) {
        List<BaseDto<T>> dtosList = new ArrayList<>();

        for (E entity : entitiesList) {
            dtosList.add(toDto(entity));
        }

        return dtosList;
    }


}