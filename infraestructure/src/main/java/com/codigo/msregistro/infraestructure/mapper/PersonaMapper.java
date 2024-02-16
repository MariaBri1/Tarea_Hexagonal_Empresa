package com.codigo.msregistro.infraestructure.mapper;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.infraestructure.entity.PersonaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public PersonaDTO mapperToPersonaDTO(PersonaEntity personaEntity) {
        return modelMapper.map(personaEntity, PersonaDTO.class);
    }

    public PersonaEntity mapperToPersonaEntity(PersonaDTO personaDTO) {
        return modelMapper.map(personaDTO, PersonaEntity.class);
    }
}
