package com.codigo.msregistro.domain.ports.out;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceOut {

    //CRUD

    //Create
    EmpresaDTO crearEmpresaOut(RequestEmpresa requestEmpresa);

    //Read
    Optional<EmpresaDTO> obtenerEmpresaOut(Long idEmpresa);

    List<EmpresaDTO> obtenerEmpresasOut();

    //Update
    EmpresaDTO actualizarEmpresaOut(Long idEmpresa, RequestEmpresa requestEmpresa);

    //Delete
    EmpresaDTO eliminarEmpresaOut(Long idEmpresa);

}
