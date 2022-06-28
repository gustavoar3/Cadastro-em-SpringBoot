package com.exercicios.Cadastro.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.exercicios.Cadastro.controllers.form.IncluirPessoaCadastro;
import com.exercicios.Cadastro.models.Pessoa;
import com.exercicios.Cadastro.repository.PessoaRepository;

@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	
	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
	public List<Pessoa> listaAniversario(){
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		String dataFormatada = formatterData.format(agora);
		
		return pessoaRepository.findByNascimento(data);
	}
	
	public List<Pessoa> listaCpf(String cpf){
		return pessoaRepository.findByCpf(cpf);
	}
	
	public void removerId(Long id){
		pessoaRepository.deleteById(id);
	}

	public Pessoa armazenar(IncluirPessoaCadastro incluirPessoaCadastro) {
		var pessoa = new Pessoa();
		
		BeanUtils.copyProperties(incluirPessoaCadastro, pessoa);
		pessoaRepository.save(pessoa);
		
		return pessoa;			
	}

	public List<Pessoa> listaTodos() {
		return pessoaRepository.findAll();
	}
}
