package com.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Produto;
import com.cursomc.dto.ProdutoDTO;
import com.cursomc.resources.utils.URL;
import com.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> find(@PathVariable("id") Integer id) {
		
		return ResponseEntity.ok().body(this.produtoService.buscar(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findPage(
				@RequestParam(value = "nome", defaultValue = "") String nome,
				@RequestParam(value = "categorias", defaultValue = "") String categorias,
				@RequestParam(value = "page", defaultValue = "0") Integer page,
				@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
				@RequestParam(value = "ordeBy", defaultValue = "nome") String ordeBy,
				@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		
	
		Page<Produto> lista = this.produtoService.buscarPagina(
				URL.decodeParam(nome),
				URL.decodeIntList(categorias), //Passando somente os Ids
				page,
				linesPerPage,
				ordeBy,
				direction);
		
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listaDTO);
	}
}
