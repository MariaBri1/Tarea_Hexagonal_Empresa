package com.codigo.msregistro.infraestructure.repository;

import com.codigo.msregistro.infraestructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {

}
