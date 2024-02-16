package com.codigo.msregistro.application.controller;

import com.codigo.msregistro.domain.aggregates.dto.PersonaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestPersona;
import com.codigo.msregistro.domain.ports.in.PersonaServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaServiceIn personaServiceIn;

    @PostMapping
    public ResponseEntity<PersonaDTO> registrar(@RequestBody RequestPersona requestPersona) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.crearPersonaIn(requestPersona));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> obtenerPersona(@PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerPersonaIn(id).get());

    }

    @GetMapping()
    public ResponseEntity<List<PersonaDTO>> obtenerPersonas() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.obtenerPersonasIn());

    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> actualizarPersona(@PathVariable Long id, @RequestBody RequestPersona requestPersona) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(personaServiceIn.actualizarPersonaIn(id, requestPersona));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonaDTO> eliminarPersona(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(personaServiceIn.eliminarPersonaIn(id));

    }
}
