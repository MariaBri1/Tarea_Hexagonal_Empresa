package com.codigo.msregistro.application.controller;

import com.codigo.msregistro.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msregistro.domain.aggregates.request.RequestEmpresa;
import com.codigo.msregistro.domain.ports.in.EmpresaServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaServiceIn empresaServiceIn;

    @PostMapping
    public ResponseEntity<EmpresaDTO> registrar(@RequestBody RequestEmpresa requestEmpresa) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.crearEmpresaIn(requestEmpresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> obtenerEmpresa(@PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(empresaServiceIn.obtenerEmpresaIn(id).get());

    }

    @GetMapping()
    public ResponseEntity<List<EmpresaDTO>> obtenerEmpresas() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(empresaServiceIn.obtenerEmpresasIn());

    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> actualizarEmpresa(@PathVariable Long id, @RequestBody RequestEmpresa requestEmpresa) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(empresaServiceIn.actualizarEmpresaIn(id, requestEmpresa));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmpresaDTO> eliminarEmpresa(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(empresaServiceIn.eliminarEmpresaIn(id));

    }
}
