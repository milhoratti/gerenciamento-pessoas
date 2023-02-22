package com.attornatus.gerenciamento.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.gerenciamento.model.Endereco;
import com.attornatus.gerenciamento.model.Pessoa;
import com.attornatus.gerenciamento.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PessoaController {

	private final PessoaService pessoaService;

	public PessoaController(@Autowired PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	@PostMapping
	public ResponseEntity<Pessoa> criarPessoa(@RequestBody @Valid Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping
	public ResponseEntity<List<Pessoa>> listarPessoas() {
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		return ResponseEntity.ok(pessoas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarPessoa(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPessoa(id);
		return ResponseEntity.ok(pessoa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody @Valid Pessoa pessoa) {
		Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoa);
		return ResponseEntity.ok(pessoaAtualizada);
	}

	@PostMapping("/{id}/endereco")
	public ResponseEntity<Endereco> salvarEndereco(@PathVariable Long id, @RequestBody @Valid Endereco endereco) {
		Endereco enderecoSalvo = pessoaService.salvarEndereco(id, endereco);
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
	}


	@GetMapping("/{id}/enderecos")
	public ResponseEntity<List<Endereco>> listarEnderecos(@PathVariable Long id) {
		List<Endereco> enderecos = pessoaService.listarEnderecos(id);
		return ResponseEntity.ok(enderecos);
	}

	@PutMapping("/{id}/endereco-principal")
	public ResponseEntity<Pessoa> definirEnderecoPrincipal(@PathVariable Long id, @RequestParam Long enderecoId) {
		Pessoa pessoa = pessoaService.definirEnderecoPrincipal(id, enderecoId);
		return ResponseEntity.ok(pessoa);
	}
}
