package com.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	//# IMPORTANTE CONSULTAR:
	//* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
										  String nome,
										  List<Categoria> categorias,
										  Pageable pageRequest);

}
