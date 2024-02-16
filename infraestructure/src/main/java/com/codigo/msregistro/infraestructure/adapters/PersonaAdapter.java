package com.codigo.msregistro.infraestructure.adapters;

import com.codigo.msregistro.domain.aggregates.constants.Constants;
import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.aggregates.response.ResponseReniec;
import com.codigo.msregistro.domain.ports.out.PersonaServiceOut;
import com.codigo.msregistro.infraestructure.entity.PersonaEntity;
import com.codigo.msregistro.infraestructure.entity.TipoDocumentoEntity;
import com.codigo.msregistro.infraestructure.mapper.PersonaMapper;
import com.codigo.msregistro.infraestructure.repository.PersonaRepository;
import com.codigo.msregistro.infraestructure.repository.TipoDocumentoRepository;
import com.codigo.msregistro.infraestructure.rest.client.ClienteReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {

    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final ClienteReniec clienteReniec;
    private final PersonaMapper personaMapper;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public PersonaDTO crearPersonaOut(RequestPersona requestPersona) {
        ResponseReniec datosReniec = this.obtenerDataReniec(requestPersona.getNumDoc());

        PersonaEntity personaGrabada = personaRepository.save(this.convertirPersonaEntity(datosReniec, requestPersona));

        return personaMapper.mapperToPersonaDTO(personaGrabada);
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaOut(Long idPersona) {
        return Optional.ofNullable(personaMapper.mapperToPersonaDTO(personaRepository.findById(idPersona).get()));
    }

    @Override
    public List<PersonaDTO> obtenerPersonasOut() {
        List<PersonaDTO> personaDTOList = new ArrayList<>();
        List<PersonaEntity> entities = personaRepository.findAll();
        for (PersonaEntity persona : entities) {
            PersonaDTO personaDTO = personaMapper.mapperToPersonaDTO(persona);
            personaDTOList.add(personaDTO);
        }
        return personaDTOList;
    }

    @Override
    public PersonaDTO actualizarPersonaOut(Long idPersona, RequestPersona requestPersona) {
        Boolean existePersona = personaRepository.existsById(idPersona);
        if (!existePersona) {
            return null;
        }

        Optional<PersonaEntity> personaEntity = personaRepository.findById(idPersona);
        ResponseReniec responseReniec = obtenerDataReniec(requestPersona.getNumDoc());

        PersonaEntity personaActualizada= new PersonaEntity();
        personaActualizada =  personaRepository.save(obtenerPersonaActualizada(responseReniec, personaEntity.get()));
        return personaMapper.mapperToPersonaDTO(personaActualizada);

    }

    @Override
    public PersonaDTO eliminarPersonaOut(Long idPersona) {
        Boolean existePersona = personaRepository.existsById(idPersona);

        if (existePersona) {
            Optional<PersonaEntity> personaEntity = personaRepository.findById(idPersona);
            personaEntity.get().setEstado(0);
            personaEntity.get().setUsuaDelet(Constants.AUDIT_ADMIN);
            personaEntity.get().setDateDelet(getTimestamp());

            personaRepository.save(personaEntity.get());
            return personaMapper.mapperToPersonaDTO(personaEntity.get());
        }
        return null;
    }

    private ResponseReniec obtenerDataReniec(String numeroDocumento) {
        String token = "Bearer " + tokenApi;
        return clienteReniec.getInfoReniec(numeroDocumento, token);
    }

    private Timestamp getTimestamp() {
        long currentTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTime);
        return timestamp;
    }

    private PersonaEntity convertirPersonaEntity(ResponseReniec responseReniec, RequestPersona requestPersona) {
        TipoDocumentoEntity tipoDocumento = tipoDocumentoRepository.findByCodTipo(requestPersona.getTipoDoc());
        

        return PersonaEntity.builder()
                .numDocu(responseReniec.getNumeroDocumento())
                .nombres(responseReniec.getNombres())
                .apePat(responseReniec.getApellidoPaterno())
                .apeMat(responseReniec.getApellidoMaterno())
                .estado(Constants.STATUS_ACTIVE)
                .usuaCrea(Constants.AUDIT_ADMIN)
                .dateCreate(this.getTimestamp())
                .tipoDocumento(tipoDocumento)
                .build();
    }

    private PersonaEntity obtenerPersonaActualizada(ResponseReniec responseReniec, PersonaEntity personaActualizar) {
        personaActualizar.setNumDocu(responseReniec.getNumeroDocumento());
        personaActualizar.setNombres(responseReniec.getNombres());
        personaActualizar.setApePat(responseReniec.getApellidoPaterno());
        personaActualizar.setApeMat(responseReniec.getApellidoMaterno());
        personaActualizar.setUsuaModif(Constants.AUDIT_ADMIN);
        personaActualizar.setDateModif(getTimestamp());

        return personaActualizar;
    }
}
