package com.attornatus.gerenciamento.service;


public class EnderecoNaoEncontradoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    
    public EnderecoNaoEncontradoException(Long idEndereco) {
        super("Endereço não encontrado com ID: " + idEndereco);
    }
    
}