package com.exercicios.Cadastro.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.exercicios.Cadastro.controllers.form.IncluirPessoaCadastro;
import com.exercicios.Cadastro.models.Pessoa;

@Service
public class PessoaService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PessoaService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
		
		for(var p : listaTodos()) {
			LocalDate dateNascimento = LocalDate.parse(p.getNascimento(), formatterData);
			
			if(dateNascimento.getDayOfYear() >= now.getDayOfYear() && dateNascimento.getDayOfYear() < now.getDayOfYear() + 7) res.add(p.getCpf());
		}
		
		return res;
	}
	
	public List<Pessoa> listaCpf(String cpf){
		String sql = "Select * From PessoaCadastro WHERE cpf=" + cpf;
		List<Pessoa> pessoas = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pessoa.class));
		
		return pessoas;
	}
	
	public void removerId(Long id){
		String sql = "Delete from PessoaCadastro where id=" + id;
		jdbcTemplate.update(sql);
	}

	public Pessoa armazenar(IncluirPessoaCadastro incluirPessoaCadastro) {
		var pessoa = new Pessoa();
		
		BeanUtils.copyProperties(incluirPessoaCadastro, pessoa);
		String sql = "INSERT INTO PessoaCadastro (nome, cpf, nascimento, tipo) VALUES (?, ?, ?, ?);";
		jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getCpf(), pessoa.getNascimento(), pessoa.getTipo());
		
		return pessoa;			
	} 

	public List<Pessoa> listaTodos() {	
		String sql = "Select * From PessoaCadastro";
		List<Pessoa> pessoas = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pessoa.class));
		
		return pessoas;
	}
}
