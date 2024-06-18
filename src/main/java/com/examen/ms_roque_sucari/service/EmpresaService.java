package com.examen.ms_roque_sucari.service;

import com.examen.ms_roque_sucari.request.EmpresaRequest;
import com.examen.ms_roque_sucari.response.ResponseBase;

public interface EmpresaService {
    ResponseBase crearEmpresa(EmpresaRequest empresaRequest);
    ResponseBase getEmpresa(String numeroDocumento);
    void deleteEmpresa(String numeroDocumento);
}
