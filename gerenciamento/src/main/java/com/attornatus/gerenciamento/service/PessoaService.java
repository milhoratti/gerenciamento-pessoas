package com.attornatus.gerenciamento.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.attornatus.gerenciamento.model.Endereco;
import com.attornatus.gerenciamento.model.Pessoa;
import com.attornatus.gerenciamento.repository.EnderecoRepository;
import com.attornatus.gerenciamento.repository.PessoaRepository;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;

    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Pessoa salvarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa buscarPessoa(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    public Pessoa atualizarPessoa(Long id, Pessoa pessoa) {
        Pessoa pessoaExistente = buscarPessoa(id);
        BeanUtils.copyProperties(pessoa, pessoaExistente, "id", "enderecos");
        return pessoaRepository.save(pessoaExistente);
    }
    
    public Endereco salvarEndereco(Long idPessoa, Endereco endereco) {
        Pessoa pessoa = buscarPessoa(idPessoa);
        pessoa.getEnderecos().add(endereco);
        pessoaRepository.save(pessoa);
        return endereco;
    }



    public List<Endereco> listarEnderecos(Long idPessoa) {
        Pessoa pessoa = buscarPessoa(idPessoa);
        return pessoa.getEnderecos();
    }

    public Pessoa definirEnderecoPrincipal(Long idPessoa, Long idEndereco) {
        Pessoa pessoa = buscarPessoa(idPessoa);
        Endereco endereco = pessoa.getEnderecos().stream()
                .filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));
        pessoa.setEnderecoPrincipal(endereco);
        return pessoaRepository.save(pessoa);
    }
}
