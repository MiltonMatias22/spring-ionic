package com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.services.exception.DataIntegrityException;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " +Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente inserir(Cliente cliente) {
		cliente.setId(null);
		cliente = this.repository.save(cliente);
		this.enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}	
	
	public Cliente atualizar(Cliente cliente) {
		Cliente newCliente = buscar(cliente.getId());
		updateData(newCliente, cliente);
		return this.repository.save(newCliente);
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setNome(cliente.getNome());
		newCliente.setEmail(cliente.getEmail());
	}


	public void deletar(Integer id) {
		buscar(id);
		try {
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("ERRO ao remover, cliente está vinculado a outros registros!");
		}
	}

	public List<Cliente> buscarTodos() {
		return this.repository.findAll();
	}
	
	public Page<Cliente> buscarPagina(Integer page, Integer linesPerPage, String ordeBy, String direction) {
		
		PageRequest pageRequest =  PageRequest.of(
																	page, linesPerPage,
																	Direction.valueOf(direction), ordeBy);
		
		return this.repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		
		Cliente cliente =new Cliente(
				null,
				clienteNewDTO.getNome(),
				clienteNewDTO.getEmail(),
				clienteNewDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteNewDTO.getTipoCliente()),
				this.bCryptPasswordEncoder.encode(clienteNewDTO.getSenha()) //* Encripitando senha
				);
		
		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		
		Endereco endereco = new Endereco(
				null,
				clienteNewDTO.getLogradouro(),
				clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(),
				clienteNewDTO.getBairro(),
				clienteNewDTO.getCep(),
				cliente,
				cidade
				);
		
		cliente.getEnderecos().add(endereco);
		
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		
		if (clienteNewDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if (clienteNewDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		return cliente;
	}
}
