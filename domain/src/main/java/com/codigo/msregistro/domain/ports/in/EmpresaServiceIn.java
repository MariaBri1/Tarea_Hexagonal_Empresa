package com.codigo.msregistro.domain.ports.in;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceIn {

    //CRUD

    //Create
    EmpresaDTO crearEmpresaIn(RequestEmpresa requestEmpresa);

    //Read
    Optional<EmpresaDTO> obtenerEmpresaIn(Long idEmpresa);

    List<EmpresaDTO> obtenerEmpresasIn();

    //Update
    EmpresaDTO actualizarEmpresaIn(Long idEmpresa, RequestEmpresa requestEmpresa);

    //Delete
    EmpresaDTO eliminarEmpresaIn(Long idEmpresa);

}
