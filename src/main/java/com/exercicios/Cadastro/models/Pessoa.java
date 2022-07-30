package com.exercicios.Cadastro.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
@Entity
@Table(name="PessoaCadastro")
public class Pessoa {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	private String nascimento;
	private String tipo;
	
	public Pessoa() {
	}
	
	public Pessoa(String nome, String cpf, String nascimento, String tipo) {
		this.nome = nome;
		this.cpf = cpf;
		this.nascimento = nascimento;
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public HttpStatus validaNome(String nome) {
		if(nome.length() < 5 || nome.length() > 80) return HttpStatus.BAD_REQUEST;
		return HttpStatus.OK;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
 
		this.cpf = cpf;
	}
	
	public HttpStatus validaCpf(String cpf) {
		if(cpf.length() != 11) return HttpStatus.BAD_REQUEST;
		return HttpStatus.OK;
	}

	public String getNascimento() {
		return nascimento;
	}
	
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
	public HttpStatus validaNascimento(String nascimento) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate niver = LocalDate.parse(nascimento, formatterData);
		
		if(now.getYear() - niver.getYear() < 18) return HttpStatus.BAD_REQUEST;
		
		return HttpStatus.OK;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
