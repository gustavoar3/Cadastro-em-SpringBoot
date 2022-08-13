package com.exercicios.Cadastro.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.exercicios.Cadastro.controllers.form.IncluirPessoaCadastro;
import com.exercicios.Cadastro.models.Pessoa;
import com.exercicios.Cadastro.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public PessoaService(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
	public HttpStatus validaDados(IncluirPessoaCadastro incluirPessoaCadastro) throws IOException{
		var pessoa = new Pessoa();
		
		if(pessoa.validaCpf(incluirPessoaCadastro.getCpf()) == HttpStatus.BAD_REQUEST 
				|| pessoa.validaNome(incluirPessoaCadastro.getNome()) == HttpStatus.BAD_REQUEST
				|| pessoa.validaNascimento(incluirPessoaCadastro.getNascimento()) == HttpStatus.BAD_REQUEST) {
			return HttpStatus.BAD_REQUEST;
		}
		
		return HttpStatus.OK;
	}
	
	public List<String> listaAniversario(){
		List<String> res = new ArrayList<String>();
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		for(var p : pessoaRepository.findAll()) {
			LocalDate dateNascimento = LocalDate.parse(p.getNascimento(), formatterData);
			
			if(dateNascimento.getDayOfYear() >= now.getDayOfYear() && dateNascimento.getDayOfYear() < now.getDayOfYear() + 7) res.add(p.getCpf());
		}
		
		return res;
	}
	
	public List<Pessoa> listaCpf(String cpf){
		if(pessoaRepository.findByCpf(cpf).size() == 0) System.out.println("Erro ao encontrar usuário. Tente novamente.");
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
