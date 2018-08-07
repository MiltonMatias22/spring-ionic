package com.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.services.exception.DataIntegrityException;
import com.cursomc.services.exception.ObjectNotFoundException;
import com.cursomc.services.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " +Cliente.class.getName()));
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
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
}
