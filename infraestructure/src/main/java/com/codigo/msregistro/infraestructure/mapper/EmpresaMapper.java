package com.codigo.msregistro.infraestructure.mapper;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msregistro.infraestructure.entity.EmpresaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmpresaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public EmpresaDTO mapperToEmpresaDTO(EmpresaEntity empresaEntity) {
        return modelMapper.map(empresaEntity, EmpresaDTO.class);
    }

    public EmpresaEntity mapperToEmpresaEntity(EmpresaDTO empresaDTO) {
        return modelMapper.map(empresaDTO, EmpresaEntity.class);
    }
}
