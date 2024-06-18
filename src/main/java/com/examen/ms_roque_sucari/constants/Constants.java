package com.examen.ms_roque_sucari.constants;

public class Constants {
    public static final Integer CODIGO_EXITO=2001;
    public static final Integer CODIGO_ERROR=2003;

    public static final String MENSAJE_EXITO="Empresa Insertada satisfactoriamente";
    public static final String MENSAJE_EXITO_DESDE_BD="Empresa Encontrada en BD";
    public static final String MENSAJE_EXITO_DESDE_REDIS="Empresa Encontrada en REDIS";
    public static final String MENSAJE_EXITO_DELETE="Empresa eliminada de REDIS";
    public static final String MENSAJE_ERROR="Ocurrio un error en la transaccion";
    public static final String AUDIT_ADMIN="RROQUE";
    public static final String REDIS_KEY_GUARDAR="API:CONSUMO:API:EXTERNA:";
}
