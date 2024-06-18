package com.examen.ms_roque_sucari.controller;

import com.examen.ms_roque_sucari.constants.Constants;
import com.examen.ms_roque_sucari.request.EmpresaRequest;
import com.examen.ms_roque_sucari.response.ResponseBase;
import com.examen.ms_roque_sucari.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }
    @PostMapping
    public ResponseEntity<ResponseBase> crearEmpresa(@RequestBody EmpresaRequest empresaRequest){
        ResponseBase responseBase = empresaService.crearEmpresa(empresaRequest);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<ResponseBase> getEmpresa(@PathVariable String numeroDocumento){
        ResponseBase responseBase = empresaService.getEmpresa(numeroDocumento);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @DeleteMapping("/{numeroDocumento}")
    public ResponseEntity<ResponseBase> deleteEmpresa(@PathVariable String numeroDocumento){
        empresaService.deleteEmpresa(numeroDocumento);
        return ResponseEntity.ok(new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO_DELETE, Optional.empty()));
    }
}
