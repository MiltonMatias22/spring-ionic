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

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> find(@PathVariable("id") Integer id) {
		
		return ResponseEntity.ok().body(this.categoriaService.buscar(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = this.categoriaService.inserir(this.categoriaService.fromDTO(categoriaDTO));
		URI uri= ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}").buildAndExpand(categoria.getId())
							.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO,
																 @PathVariable("id") Integer id){
		Categoria categoria = this.categoriaService.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = this.categoriaService.atualizar(categoria);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
		this.categoriaService.deletar(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> lista = this.categoriaService.buscarTodos();
		List<CategoriaDTO> listaDTO = lista
				.stream()
				.map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
				@RequestParam(value = "page", defaultValue = "0") Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
				@RequestParam(value = "ordeBy", defaultValue = "nome") String ordeBy,
				@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		
		Page<Categoria> lista = this.categoriaService.buscarPagina(page, linesPerPage, ordeBy, direction);
		Page<CategoriaDTO> listaDTO = lista.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listaDTO);
	}
	
	
}
