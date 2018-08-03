package com.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.services.repositories.CategoriaRepository;
import com.cursomc.services.repositories.CidadeRepository;
import com.cursomc.services.repositories.ClienteRepository;
import com.cursomc.services.repositories.EnderecoRepository;
import com.cursomc.services.repositories.EstadoRepository;
import com.cursomc.services.repositories.ProdutoRepository;

@SpringBootApplication
public class SpringIonicApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringIonicApplication.class, args);
	}

	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		/*Categoria */
		Categoria cat1 = new Categoria(null, "Informática");		
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.0);
		Produto prod2 = new Produto(null, "Impressora", 800.0);
		Produto prod3 = new Produto(null, "Mouse", 70.0);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		this.produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		/*------------------- fim categoria ----------------*/
		
		/*Cidade e estado */
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlâdia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		this.estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		
		this.cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		/*----------------- fim cidade e estado -----------------*/
		
		/*---------------------cliente Endereco----------------*/
		
		Cliente cliente = new Cliente(null, "Maria", "@Maria.com", "11122233344", TipoCliente.PESSOAFISICA);
		
		cliente.getTelefones().addAll(Arrays.asList("982664367","982664368"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "66025160", cliente, cidade1);
		Endereco endereco2 = new Endereco(null, "AV. Motas", "105", "Sala 800", "Centro", "66025160", cliente, cidade2);
		
		cliente.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		this.clienteRepository.saveAll(Arrays.asList(cliente));
		
		this.enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		/*--------------------fim cliente e endereço ------------------------*/
		
		
	}
}
