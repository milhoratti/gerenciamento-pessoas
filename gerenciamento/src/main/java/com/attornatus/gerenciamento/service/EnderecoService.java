package com.attornatus.gerenciamento.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attornatus.gerenciamento.model.Endereco;
import com.attornatus.gerenciamento.repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
    
    public Endereco buscarEndereco(Long id) {
        return enderecoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado com id " + id));
    }
    
    public List<Endereco> listarEnderecos() {
        return enderecoRepository.findAll();
    }
    
    public void excluirEndereco(Long id) {
        enderecoRepository.deleteById(id);
    }
}
