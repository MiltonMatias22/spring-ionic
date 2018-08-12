package com.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.Pagamento;
import com.cursomc.domain.PagamentoBoleto;
import com.cursomc.domain.PagamentoCartao;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.repositories.EstadoRepository;
import com.cursomc.repositories.ItemPedidoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
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
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public void instantiateTestDataBase() throws ParseException {
		
		/*Categoria */
		Categoria cat1 = new Categoria(null, "Informática");		
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");		
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");		
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto prod1 = new Produto(null, "Computador", 2000.0);
		Produto prod2 = new Produto(null, "Impressora", 800.0);
		Produto prod3 = new Produto(null, "Mouse", 70.0);
		Produto prod4 = new Produto(null, "Mesa de Escritório", 300.0);
		Produto prod5 = new Produto(null, "Toalha", 50.0);
		Produto prod6 = new Produto(null, "Colcha", 300.0);
		Produto prod7 = new Produto(null, "TV true color", 1200.0);
		Produto prod8 = new Produto(null, "Roçadeira", 800.0);
		Produto prod9 = new Produto(null, "Abajour", 100.0);
		Produto prod10 = new Produto(null, "Pendente", 180.0);
		Produto prod11 = new Produto(null, "Champoo", 90.0);
		
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2, prod4));
		cat3.getProdutos().addAll(Arrays.asList(prod5, prod6));
		cat4.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3, prod7));
		cat5.getProdutos().addAll(Arrays.asList(prod8));
		cat6.getProdutos().addAll(Arrays.asList(prod9, prod10));
		cat7.getProdutos().addAll(Arrays.asList(prod11));

		prod1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		prod3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prod4.getCategorias().addAll(Arrays.asList(cat2));
		prod5.getCategorias().addAll(Arrays.asList(cat3));
		prod6.getCategorias().addAll(Arrays.asList(cat3));
		prod7.getCategorias().addAll(Arrays.asList(cat4));
		prod8.getCategorias().addAll(Arrays.asList(cat5));
		prod9.getCategorias().addAll(Arrays.asList(cat6));
		prod10.getCategorias().addAll(Arrays.asList(cat6));
		prod11.getCategorias().addAll(Arrays.asList(cat7));
		
		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6,cat7));
		
		this.produtoRepository.saveAll(Arrays.asList(
				prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10, prod1));
		
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
		
		Cliente cliente = new Cliente(
					null,
					"Milton Matias",
					"nodout.develops@gmail.com",
					"11122233344",
					TipoCliente.PESSOAFISICA,
					this.bCryptPasswordEncoder.encode("123")
				);
		
		cliente.getTelefones().addAll(Arrays.asList("982664367","982664368"));
		
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "66025160", cliente, cidade1);
		Endereco endereco2 = new Endereco(null, "AV. Motas", "105", "Sala 800", "Centro", "66025160", cliente, cidade2);
		
		cliente.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		this.clienteRepository.saveAll(Arrays.asList(cliente));
		
		this.enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		/*--------------------fim cliente e endereço ------------------------*/
		
		/* ------------- Pedido e pagamento --------------*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:30"), cliente, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 10:30"), cliente, endereco2);
		
		Pagamento pagametocartao = new PagamentoCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagametocartao);
		Pagamento pagamentoBoleto = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, pedido2,
				sdf.parse("20/10/2017 00:00"), null);		
		pedido2.setPagamento(pagamentoBoleto);
		
		cliente.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		this.pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		
		this.pagamentoRepository.saveAll(Arrays.asList(pagametocartao, pagamentoBoleto));
		
		/*--------------------fim Pedido e pagamento ---------------*/
		
		/*-------------- ItemPedido -------------------*/
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, prod1, 0.0, 1, 2000.0);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, prod3, 0.0, 2, 80.0);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, prod2, 100.0, 1, 800.0);
		
		pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		prod1.getItens().addAll(Arrays.asList(itemPedido1));
		prod2.getItens().addAll(Arrays.asList(itemPedido3));
		prod3.getItens().addAll(Arrays.asList(itemPedido2));
		
		this.itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
				
		/*--------------------fim ItemPedido ---------------*/
		
	}
}
