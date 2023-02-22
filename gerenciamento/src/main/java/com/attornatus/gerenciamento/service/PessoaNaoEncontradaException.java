package com.attornatus.gerenciamento.service;


public class PessoaNaoEncontradaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	
    public PessoaNaoEncontradaException(Long id) {
        super("Pessoa com o id " + id + " não encontrada.");
    }
}