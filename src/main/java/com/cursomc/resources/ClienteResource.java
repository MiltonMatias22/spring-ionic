package com.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Cliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable("id") Integer id) {
		
		return ResponseEntity.ok().body(this.clienteService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
		Cliente cliente = this.clienteService.inserir(this.clienteService.fromDTO(clienteNewDTO));
		URI uri= ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}").buildAndExpand(cliente.getId())
							.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO,
																 @PathVariable("id") Integer id){
		Cliente cliente = this.clienteService.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = this.clienteService.atualizar(cliente);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		this.clienteService.deletar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> lista = this.clienteService.buscarTodos();
		List<ClienteDTO> listaDTO = lista
				.stream()
				.map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
				@RequestParam(value = "page", defaultValue = "0") Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
				@RequestParam(value = "ordeBy", defaultValue = "nome") String ordeBy,
				@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		
		Page<Cliente> lista = this.clienteService.buscarPagina(page, linesPerPage, ordeBy, direction);
		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listaDTO);
	}
}
