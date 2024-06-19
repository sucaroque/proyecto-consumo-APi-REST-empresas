package com.examen.ms_roque_sucari.controller;

import com.examen.ms_roque_sucari.constants.Constants;
import com.examen.ms_roque_sucari.entity.EmpresaEntity;
import com.examen.ms_roque_sucari.request.EmpresaRequest;
import com.examen.ms_roque_sucari.response.ResponseBase;
import com.examen.ms_roque_sucari.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/empresa")
@Tag(
        name = "API DE MANTENIMIENTO DE EMPRESA",
        description = "Esta api contine todos los endPoint para el mantenimiento de la entidad empresa"
)
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }
    @PostMapping
    @Operation(summary = "Crear una Empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa creada exitosamente",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = EmpresaEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request, el request no cumple con lo esperado",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = EmpresaEntity.class))})
    })
    public ResponseEntity<ResponseBase> crearEmpresa(@RequestBody EmpresaRequest empresaRequest){
        ResponseBase responseBase = empresaService.crearEmpresa(empresaRequest);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @GetMapping("/{numeroDocumento}")
    @Operation(summary = "Busca una empresa mediante su documento(RUC) y ENVIA A REDIS 10 MINUTOS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Not found, Empresa no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))})
    })
    public ResponseEntity<ResponseBase> getEmpresaPorDocumentoRuc(@PathVariable String numeroDocumento){
        ResponseBase responseBase = empresaService.getEmpresa(numeroDocumento);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @DeleteMapping("/{numeroDocumento}")
    @Operation(summary = "Elimina una empresa mediante su documento(RUC)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa eliminada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))}),
            @ApiResponse(responseCode = "404", description = "Not found, Empresa no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmpresaEntity.class))})
    })
    public ResponseEntity<ResponseBase> deleteEmpresa(@PathVariable String numeroDocumento){
        empresaService.deleteEmpresa(numeroDocumento);
        return ResponseEntity.ok(new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO_DELETE, Optional.empty()));
    }
}
