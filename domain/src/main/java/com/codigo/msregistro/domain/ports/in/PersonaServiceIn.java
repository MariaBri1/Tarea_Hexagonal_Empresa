package com.codigo.msregistro.domain.ports.in;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {

    //CRUD

    //Create
    PersonaDTO crearPersonaIn(RequestPersona requestPersona);

    //Read
    Optional<PersonaDTO> obtenerPersonaIn(Long idPersona);

    List<PersonaDTO> obtenerPersonasIn();

    //Update
    PersonaDTO actualizarPersonaIn(Long idPersona, RequestPersona requestPersona);

    //Delete
    PersonaDTO eliminarPersonaIn(Long idPersona);

}
