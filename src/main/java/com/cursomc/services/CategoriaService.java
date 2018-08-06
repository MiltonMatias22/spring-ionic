package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.services.exception.DataIntegrityException;
import com.cursomc.services.exception.ObjectNotFoundException;
import com.cursomc.services.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return this.repository.save(categoria);
	}

	public Categoria atualizar(Categoria categoria) {
		buscar(categoria.getId());
		return this.repository.save(categoria);
	}

	public void deletar(Integer id) {
		buscar(id);
		try {
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("ERRO ou remover registro, categoria está vinculada a produtos!");
		}
	}
}
