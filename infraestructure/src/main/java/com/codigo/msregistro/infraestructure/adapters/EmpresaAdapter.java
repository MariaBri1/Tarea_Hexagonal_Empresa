package com.codigo.msregistro.infraestructure.adapters;

import com.codigo.msregistro.domain.aggregates.constants.Constants;
import com.codigo.msregistro.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;
import com.codigo.msregistro.domain.aggregates.response.ResponseSunat;
import com.codigo.msregistro.domain.ports.out.EmpresaServiceOut;
import com.codigo.msregistro.infraestructure.entity.EmpresaEntity;
import com.codigo.msregistro.infraestructure.entity.TipoDocumentoEntity;
import com.codigo.msregistro.infraestructure.mapper.EmpresaMapper;
import com.codigo.msregistro.infraestructure.repository.EmpresaRepository;
import com.codigo.msregistro.infraestructure.repository.TipoDocumentoRepository;
import com.codigo.msregistro.infraestructure.rest.client.ClienteSunat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaAdapter implements EmpresaServiceOut {

    private final EmpresaRepository empresaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final ClienteSunat clienteSunat;
    private final EmpresaMapper empresaMapper;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public EmpresaDTO crearEmpresaOut(RequestEmpresa requestEmpresa) {
        ResponseSunat datosSunat = this.obtenerDataSunat(requestEmpresa.getNumDoc());

        EmpresaEntity empresaGrabada = empresaRepository.save(this.convertirEmpresaEntity(datosSunat, requestEmpresa));

        return empresaMapper.mapperToEmpresaDTO(empresaGrabada);
    }

    @Override
    public Optional<EmpresaDTO> obtenerEmpresaOut(Long idEmpresa) {
        return Optional.ofNullable(empresaMapper.mapperToEmpresaDTO(empresaRepository.findById(idEmpresa).get()));
    }

    @Override
    public List<EmpresaDTO> obtenerEmpresasOut() {
        List<EmpresaDTO> empresaDTOList = new ArrayList<>();
        List<EmpresaEntity> entities = empresaRepository.findAll();

        for (EmpresaEntity empresaEntity : entities) {
            EmpresaDTO empresaDTO = empresaMapper.mapperToEmpresaDTO(empresaEntity);
            empresaDTOList.add(empresaDTO);
        }

        return empresaDTOList;
    }

    @Override
    public EmpresaDTO actualizarEmpresaOut(Long idEmpresa, RequestEmpresa requestEmpresa) {
        Boolean existeEmpresa = empresaRepository.existsById(idEmpresa);

        if (!existeEmpresa) {
            return null;
        }

        Optional<EmpresaEntity> empresaEntity = empresaRepository.findById(idEmpresa);
        ResponseSunat responseSunat = this.obtenerDataSunat(requestEmpresa.getNumDoc());

        EmpresaEntity empresaActualizada = empresaRepository.save(obtenerEmpresaActualizada(responseSunat, empresaEntity.get()));

        return empresaMapper.mapperToEmpresaDTO(empresaActualizada);
    }

    @Override
    public EmpresaDTO eliminarEmpresaOut(Long idEmpresa) {
        Boolean existeEmpresa = empresaRepository.existsById(idEmpresa);

        if (!existeEmpresa) {
            return null;
        }

        EmpresaEntity empresaEncontrada = empresaRepository.findById(idEmpresa).get();
        empresaEncontrada.setEstado(0);
        empresaEncontrada.setUsuaDelet(Constants.AUDIT_ADMIN);
        empresaEncontrada.setDateDelet(getTimestamp());

        EmpresaEntity empresaEliminada = empresaRepository.save(empresaEncontrada);

        return empresaMapper.mapperToEmpresaDTO(empresaEliminada);
    }

    private ResponseSunat obtenerDataSunat(String numeroDocumento) {
        String token = "Bearer " + tokenApi;
        return clienteSunat.getInfoSunat(numeroDocumento, token);
    }

    private Timestamp getTimestamp() {
        long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }

    private EmpresaEntity convertirEmpresaEntity(ResponseSunat responseSunat, RequestEmpresa requestEmpresa) {
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestEmpresa.getTipoDoc());

        return EmpresaEntity.builder()
                .numDocu(responseSunat.getNumeroDocumento())
                .razonSocial(responseSunat.getRazonSocial())
                .nomComercial(responseSunat.getRazonSocial())
                .estado(Constants.STATUS_ACTIVE)
                .usuaCrea(Constants.AUDIT_ADMIN)
                .dateCreate(this.getTimestamp())
                .tipoDocumento(tipoDocumento)
                .build();
    }

    private EmpresaEntity obtenerEmpresaActualizada(ResponseSunat responseSunat, EmpresaEntity empresaActualizar) {
        empresaActualizar.setNumDocu(responseSunat.getNumeroDocumento());
        empresaActualizar.setRazonSocial(responseSunat.getRazonSocial());
        empresaActualizar.setNomComercial(responseSunat.getRazonSocial());
        empresaActualizar.setEstado(Constants.STATUS_ACTIVE);
        empresaActualizar.setUsuaModif(Constants.AUDIT_ADMIN);
        empresaActualizar.setDateModif(getTimestamp());

        return empresaActualizar;
    }
}
