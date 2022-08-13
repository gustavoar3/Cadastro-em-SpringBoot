package com.exercicios.Cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exercicios.Cadastro.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	List<Pessoa> findByNascimento(String data);
	
	List<Pessoa> findByCpf(String cpf);
	
	@Query("Select p from Pessoa p")
	List<Pessoa> selectAll();
	
}
