package com.examen.ms_roque_sucari.util;

import com.examen.ms_roque_sucari.entity.EmpresaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    public static String convertirAString(EmpresaEntity empresa){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(empresa);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertirDesdeString(String json, Class<T> tipoClasse){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json,tipoClasse);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
