package com.examen.ms_roque_sucari.service.impl;

import com.examen.ms_roque_sucari.clients.ClientSunat;
import com.examen.ms_roque_sucari.constants.Constants;
import com.examen.ms_roque_sucari.dao.EmpresaRepository;
import com.examen.ms_roque_sucari.entity.EmpresaEntity;
import com.examen.ms_roque_sucari.redis.RedisService;
import com.examen.ms_roque_sucari.request.EmpresaRequest;
import com.examen.ms_roque_sucari.response.ResponseBase;
import com.examen.ms_roque_sucari.response.ResponseSunat;
import com.examen.ms_roque_sucari.service.EmpresaService;

import com.examen.ms_roque_sucari.util.Util;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x509.sigi.PersonalData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpresaServicesImpl implements EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ClientSunat clientSunat;
    private final RedisService redisService;

    //SunatService apiService = SunatCliente.getClient().create(SunatService.class);
    @Value("${token.api}")
    private String tokenApi;

    @Override
    public ResponseBase crearEmpresa(EmpresaRequest empresaRequest) {
        EmpresaEntity empresaEntity = getEntity(empresaRequest);
        if (empresaEntity != null) {
            empresaRepository.save(empresaEntity);
            return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO, Optional.of(empresaEntity));
        }else{
            return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR, Optional.empty());
        }
    }

    @Override
    public ResponseBase getEmpresa(String numeroDocumento) {
        String redisInfo =redisService.getFromRedis(Constants.REDIS_KEY_GUARDAR+numeroDocumento);
        if (redisInfo != null) {
            EmpresaEntity empresa = Util.convertirDesdeString(redisInfo, EmpresaEntity.class);
            return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO_DESDE_REDIS, Optional.of(empresa));
        }else {
            EmpresaEntity empresa = empresaRepository.findByNumeroDocumento(numeroDocumento);
            String dataParaRedis = Util.convertirAString(empresa);
            redisService.saveInRedis(Constants.REDIS_KEY_GUARDAR+numeroDocumento,dataParaRedis,10);
            return new ResponseBase(Constants.CODIGO_EXITO, Constants.MENSAJE_EXITO_DESDE_BD, Optional.of(empresa));
        }
    }

    @Override
    public void deleteEmpresa(String numeroDocumento) {
        redisService.deleteKey(Constants.REDIS_KEY_GUARDAR+numeroDocumento);
    }

    private ResponseSunat getExecutionSunat(String ruc){
        String auth = "Bearer " + tokenApi;
        ResponseSunat sunat = clientSunat.getInfoSunat(ruc, auth);
        return sunat;
    }
    private EmpresaEntity getEntity(EmpresaRequest empresaRequest){
        EmpresaEntity empresaEntity = new EmpresaEntity();
        ResponseSunat responseSunat = getExecutionSunat(empresaRequest.getRuc());
        if (responseSunat != null){
            empresaEntity.setRazonSocial(responseSunat.getRazonSocial());
            empresaEntity.setTipoDocumento(responseSunat.getTipoDocumento());
            empresaEntity.setNumeroDocumento(responseSunat.getNumeroDocumento());
            empresaEntity.setEstado(responseSunat.getEstado());
            empresaEntity.setCondicion(responseSunat.getCondicion());
            empresaEntity.setDireccion(responseSunat.getDireccion());
            empresaEntity.setUbigeo(responseSunat.getUbigeo());
            empresaEntity.setViaTipo(responseSunat.getViaTipo());
            empresaEntity.setViaNombre(responseSunat.getViaNombre());
            empresaEntity.setZonaCodigo(responseSunat.getZonaCodigo());
            empresaEntity.setZonaTipo(responseSunat.getZonaTipo());
            empresaEntity.setNumero(responseSunat.getNumero());
            empresaEntity.setInterior(responseSunat.getInterior());
            empresaEntity.setLote(responseSunat.getLote());
            empresaEntity.setDepartamento(responseSunat.getDepartamento());
            empresaEntity.setManzana(responseSunat.getManzana());
            empresaEntity.setKilometro(responseSunat.getKilometro());
            empresaEntity.setDistrito(responseSunat.getDistrito());
            empresaEntity.setProvincia(responseSunat.getProvincia());
            empresaEntity.setDepartamento(responseSunat.getDepartamento());
            empresaEntity.setEsAgenteRetencion(responseSunat.isEsAgenteRetencion());
            empresaEntity.setUsuarioCreacion(Constants.AUDIT_ADMIN);
            empresaEntity.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            return empresaEntity;
        }else {
            return null;
        }
    }
}
