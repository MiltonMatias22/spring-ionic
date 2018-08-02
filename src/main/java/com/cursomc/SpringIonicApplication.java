package com.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.services.repositories.CategoriaRepository;

@SpringBootApplication
public class SpringIonicApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringIonicApplication.class, args);
	}

	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		/*Categoria */
		Categoria cat1 = new Categoria(1, "Informática");		
		Categoria cat2 = new Categoria(2, "Escritório");
		
		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		/*------------------- fim categoria ----------------*/
		
	}
}
