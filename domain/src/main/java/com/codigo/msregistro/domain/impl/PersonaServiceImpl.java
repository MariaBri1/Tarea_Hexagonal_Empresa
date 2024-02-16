package com.codigo.msregistro.domain.impl;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.ports.in.PersonaServiceIn;
import com.codigo.msregistro.domain.ports.out.PersonaServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {

    private final PersonaServiceOut personaServiceOut;

    @Override
    public PersonaDTO crearPersonaIn(RequestPersona requestPersona) {
        return personaServiceOut.crearPersonaOut(requestPersona);
    }

    @Override
    public Optional<PersonaDTO> obtenerPersonaIn(Long idPersona) {
        return personaServiceOut.obtenerPersonaOut(idPersona);
    }

    @Override
    public List<PersonaDTO> obtenerPersonasIn() {
        return personaServiceOut.obtenerPersonasOut();
    }

    @Override
    public PersonaDTO actualizarPersonaIn(Long idPersona, RequestPersona requestPersona) {
        return personaServiceOut.actualizarPersonaOut(idPersona, requestPersona);
    }

    @Override
    public PersonaDTO eliminarPersonaIn(Long idPersona) {
        return personaServiceOut.eliminarPersonaOut(idPersona);
    }
}
