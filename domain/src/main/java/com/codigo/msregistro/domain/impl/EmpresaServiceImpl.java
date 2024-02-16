package com.codigo.msregistro.domain.impl;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;
import com.codigo.msregistro.domain.ports.in.EmpresaServiceIn;
import com.codigo.msregistro.domain.ports.out.EmpresaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaServiceIn {

    private final EmpresaServiceOut personaServiceOut;

    @Override
    public EmpresaDTO crearEmpresaIn(RequestEmpresa requestEmpresa) {
        return personaServiceOut.crearEmpresaOut(requestEmpresa);
    }

    @Override
    public Optional<EmpresaDTO> obtenerEmpresaIn(Long idEmpresa) {
        return personaServiceOut.obtenerEmpresaOut(idEmpresa);
    }

    @Override
    public List<EmpresaDTO> obtenerEmpresasIn() {
        return personaServiceOut.obtenerEmpresasOut();
    }

    @Override
    public EmpresaDTO actualizarEmpresaIn(Long idEmpresa, RequestEmpresa requestEmpresa) {
        return personaServiceOut.actualizarEmpresaOut(idEmpresa, requestEmpresa);
    }

    @Override
    public EmpresaDTO eliminarEmpresaIn(Long idEmpresa) {
        return personaServiceOut.eliminarEmpresaOut(idEmpresa);
    }
}