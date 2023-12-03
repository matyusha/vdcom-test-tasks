package com.example.services;

import com.example.entities.CustomerEntity;
import com.example.models.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerDto mapToDto(CustomerEntity entity);

    @Mapping(target = "id", ignore = true)
    CustomerEntity createNew(CustomerDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget CustomerEntity entity, CustomerDto dto);

}
