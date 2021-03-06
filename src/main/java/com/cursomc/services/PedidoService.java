package com.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Cliente;
import com.cursomc.domain.ItemPedido;
import com.cursomc.domain.PagamentoBoleto;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.enums.EstadoPagamento;
import com.cursomc.repositories.ItemPedidoRepository;
import com.cursomc.repositories.PagamentoRepository;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.security.UserSS;
import com.cursomc.services.exception.AuthorizationException;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> categoria = pedidoRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", TitemPedidoo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido inserir( Pedido pedido) {
		
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(this.clienteService.buscar(pedido.getCliente().getId()));
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
			this.boletoService.preencherPagamentoBoleto(pagamentoBoleto, pedido.getInstante());

		}

		pedido = this.pedidoRepository.save(pedido);

		this.pagamentoRepository.save(pedido.getPagamento());

		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(this.produtoService.buscar(itemPedido.getProduto().getId()));
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(pedido);

		}

		this.itemPedidoRepository.saveAll(pedido.getItens());
		
		//*Enviando email
		// this.emailService.sendOrderConfirmationEmail(pedido);
		this.emailService.sendOrderConfirmationHtmlEmail(pedido);
		
		return pedido;
	}
	
	//*Cliente logado listando somente seus pedidos
	public Page<Pedido> buscarPagina(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Cliente cliente =  this.clienteService.buscar(user.getId());
		
		return this.pedidoRepository.findByCliente(cliente, pageRequest);
	}
	
	
}
