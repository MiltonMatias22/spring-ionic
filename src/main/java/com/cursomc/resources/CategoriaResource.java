package com.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Categoria;
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
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria){
		categoria = this.categoriaService.inserir(categoria);
		URI uri= ServletUriComponentsBuilder
							.fromCurrentRequest()
							.path("/{id}").buildAndExpand(categoria.getId())
							.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody Categoria categoria,
																 @PathVariable("id") Integer id){
		categoria.setId(id);
		categoria = this.categoriaService.atualizar(categoria);
		
		return ResponseEntity.noContent().build();
		
	}
}
