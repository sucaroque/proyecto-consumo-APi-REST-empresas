package com.examen.ms_roque_sucari.dao;

import com.examen.ms_roque_sucari.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    EmpresaEntity findByNumeroDocumento(String numeroDocumento);
}
