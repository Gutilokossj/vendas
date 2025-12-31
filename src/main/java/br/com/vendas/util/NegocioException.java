package br.com.vendas.util;

import java.io.Serial;

public class NegocioException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public NegocioException(String message){
        super(message);
    }
}
