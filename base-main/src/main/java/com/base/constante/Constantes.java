package com.base.constante;

/**
 *
 * @author Ayslan
 */
public class Constantes {

    /**
     * Tipo para String sem tamanho definido.
     * Postgres: text
     * Oracle: clob
     * MySQL: longtext
     */
    public static final String PERSISTENCE_UNIT_NAME = "basePU";
    public static final String TIPO_TEXTO_BANCO = "text";
    public static final int MINUTOS_VALIDADE_RECUPERACAO_SENHA = 30;

    private Constantes() {
    }
}
