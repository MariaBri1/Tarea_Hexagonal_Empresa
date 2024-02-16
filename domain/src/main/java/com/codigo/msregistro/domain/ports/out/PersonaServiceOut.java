package com.codigo.msregistro.domain.ports.out;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {

    //CRUD

    //Create
    PersonaDTO crearPersonaOut(RequestPersona requestPersona);

    //Read
    Optional<PersonaDTO> obtenerPersonaOut(Long idPersona);

    List<PersonaDTO> obtenerPersonasOut();

    //Update
    PersonaDTO actualizarPersonaOut(Long idPersona, RequestPersona requestPersona);

    //Delete
    PersonaDTO eliminarPersonaOut(Long idPersona);

}
