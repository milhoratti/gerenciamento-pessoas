package com.attornatus.gerenciamento.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.attornatus.gerenciamento.model.Endereco;
import com.attornatus.gerenciamento.model.Pessoa;
import com.attornatus.gerenciamento.repository.EnderecoRepository;
import com.attornatus.gerenciamento.repository.PessoaRepository;

import java.util.ArrayList;

@Service
public class PessoaService {

	private final PessoaRepository pessoaRepository;
	// private final EnderecoRepository enderecoRepository;

	public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
		this.pessoaRepository = pessoaRepository;
		// this.enderecoRepository = enderecoRepository;
	}

	public Pessoa salvarPessoa(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public List<Pessoa> listarPessoas() {
		return pessoaRepository.findAll();
	}

	public Pessoa buscarPessoa(Long id) {
		return pessoaRepository.findById(id).orElseThrow(() -> new PessoaNaoEncontradaException(id));
	}

	public Pessoa atualizarPessoa(Long id, Pessoa pessoa) {
		Pessoa pessoaExistente = buscarPessoa(id);
		BeanUtils.copyProperties(pessoa, pessoaExistente, "id", "enderecos");
		return pessoaRepository.save(pessoaExistente);
	}

	public Endereco salvarEndereco(Long idPessoa, Endereco endereco) {
		Pessoa pessoa = buscarPessoa(idPessoa);
		pessoa.getEnderecos().add(endereco);

		// Se o endereço não for marcado como principal, define o primeiro como
		// principal
		if (!endereco.isEnderecoPrincipal() && pessoa.getEnderecos().size() == 1) {
			pessoa.getEnderecos().get(0).setEnderecoPrincipal(true);
		}

		pessoaRepository.save(pessoa);
		return endereco;
	}

	public List<Endereco> listarEnderecos(Long idPessoa) {
		Pessoa pessoa = buscarPessoa(idPessoa);

		if (pessoa.getEnderecos() == null || pessoa.getEnderecos().isEmpty()) {
			// retorna uma lista vazia se a lista de endereços for nula ou vazia
			return new ArrayList<>();
		}

		// Filtra a lista de endereços para retornar apenas o endereço principal
		List<Endereco> enderecos = pessoa.getEnderecos();
		if (enderecos == null || enderecos.isEmpty()) {
			// retorna uma lista vazia se a lista de endereços for nula ou vazia
			return new ArrayList<>();
		}

		// Se não houver endereço principal, retorna o primeiro da lista
		if (enderecos.isEmpty()) {
			enderecos.add(pessoa.getEnderecos().get(0));
		}

		return enderecos;
	}

	public Pessoa definirEnderecoPrincipal(Long idPessoa, Long idEndereco) {
	    Pessoa pessoa = buscarPessoa(idPessoa);
	    Endereco endereco = pessoa.getEnderecos().stream()
	        .filter(e -> e.getId().equals(idEndereco))
	        .findFirst()
	        .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));

	    pessoa.getEnderecos().forEach(e -> {
	        if (e.isEnderecoPrincipal() && !e.equals(endereco)) {
	            e.setEnderecoPrincipal(false);
	        }
	    });

	    endereco.setEnderecoPrincipal(true);

	    pessoaRepository.save(pessoa);

	    return pessoa;
	}





}