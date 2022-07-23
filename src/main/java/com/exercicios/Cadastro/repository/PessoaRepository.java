package com.exercicios.Cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exercicios.Cadastro.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Query("Select * From PessoaCadastro")
	List<Pessoa> selectAll();
	
	@Query("Insert into PessoaCadastro (id, nome, cpf, nascimento, tipo) Values (:pessoa.getId(), :pessoa.getCpf(), :pessoa.getNome(), :pessoa.getNascimento(), :pessoa.getTipo())")
	void saveCadastro(Pessoa pessoa);

	void deleteId(Long id);

	List<Pessoa> selectCpf(String cpf);
	
}
