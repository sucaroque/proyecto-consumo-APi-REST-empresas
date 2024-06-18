package com.examen.ms_roque_sucari.dao;

import com.examen.ms_roque_sucari.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
}
