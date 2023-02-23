package com.attornatus.gerenciamento.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PessoaServiceTest {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Test
	public void testSalvarPessoa() {
		// Cria uma pessoa de teste
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Teste");
		pessoa.setEmail("teste@teste.com");
		pessoa.setTelefone("1111111111");

		// Salva a pessoa
		Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);

		// Verifica se a pessoa foi salva corretamente
		assertEquals(pessoaSalva, pessoaRepository.findById(pessoaSalva.getId()).get());
	}

	@Test
	public void testListarPessoas() {
		// Cria uma lista de pessoas de teste
		Pessoa pessoa1 = new Pessoa();
		pessoa1.setNome("Teste 1");
		pessoa1.setEmail("teste1@teste.com");
		pessoa1.setTelefone("1111111111");

		Pessoa pessoa2 = new Pessoa();
		pessoa2.setNome("Teste 2");
		pessoa2.setEmail("teste2@teste.com");
		pessoa2.setTelefone("2222222222");

		// Salva as pessoas
		pessoaService.salvarPessoa(pessoa1);
		pessoaService.salvarPessoa(pessoa2);

		// Verifica se a lista de pessoas contém as duas pessoas criadas acima
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		assertTrue(pessoas.contains(pessoa1));
		assertTrue(pessoas.contains(pessoa2));
	}

	@Test
	public void testBuscarPessoa() {
		// Cria uma pessoa de teste
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Teste");
		pessoa.setEmail("teste@teste.com");
		pessoa.setTelefone("1111111111");

		// Salva a pessoa
		pessoaService.salvarPessoa(pessoa);

		// Busca a pessoa salva
		Pessoa pessoaEncontrada = pessoaService.buscarPessoa(pessoa.getId());

		// Verifica se a pessoa encontrada é a mesma que foi salva
		assertEquals(pessoaEncontrada, pessoa);
	}

	// Teste para atualização de pessoa
	@Test
	public void testAtualizarPessoa() {
		// Cria uma nova pessoa
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Maria");
		pessoa.setEmail("maria@teste.com");
		pessoa.setDataNascimento(LocalDate.of(1990, 10, 15));

		// Salva a pessoa no repositório
		Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);

		// Atualiza a pessoa com novas informações
		pessoaSalva.setNome("Maria Silva");
		pessoaSalva.setEmail("mariasilva@teste.com");
		pessoaSalva.setDataNascimento(LocalDate.of(1990, 10, 15));

		// Chama o método de atualização de pessoa
		Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(pessoaSalva.getId(), pessoaSalva);

		// Verifica se a pessoa foi atualizada corretamente
		assertEquals("Maria Silva", pessoaAtualizada.getNome());
		assertEquals("mariasilva@teste.com", pessoaAtualizada.getEmail());
		assertEquals(LocalDate.of(1990, 10, 15), pessoaAtualizada.getDataNascimento());
	}

	// Teste para definição de endereço principal de uma pessoa
	@Test
    public void testDefinirEnderecoPrincipal() {
        //Cria uma nova pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria");
        pessoa.setEmail("maria@teste.com");
        pessoa.setDataNascimento(LocalDate.of(1990, 10, 15));
        
        //Cria um endereço para a pessoa
        Endereco endereco1 = new Endereco();
        endereco1.setRua("Rua 1");
        endereco1.setNumero("100");
        endereco1.setBairro("Centro");
        endereco1.setCidade("São Paulo");
        endereco1.setEstado("SP");
        endereco1.setCep("01000-000");
        
        //Cria outro endereço para a pessoa
        Endereco endereco2 = new Endereco();
        endereco2.setRua("Rua 2");
        endereco2.setNumero("200");
        endereco2.setBairro("Centro");
        endereco2.setCidade("São Paulo");
        endereco2.setEstado("SP");
        endereco2.setCep("02000-000");
        
        //Adiciona os endereços à lista de endereços da pessoa
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco1);
        enderecos.add(endereco2);
        pessoa.setEnderecos(enderecos);
        
        //Salva a pessoa no repositório
        Pessoa pessoaSalva = pessoaService.salvarPessoa(pessoa);
        
        //Define o endereço 2 como endereço principal
        Pessoa pessoaAtualizada = pessoaService.definirEnderecoPrincipal(pessoaSalva.getId(), endereco2.getId());
        
        //Verifica se o endereço principal foi definido corretamente
        assertEquals(endereco2, pessoaAtualizada.getEnderecoPrincipal());
    }
