package com.exercicios.Cadastro.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
@Entity
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
		if(validaNome(nome) == HttpStatus.OK) this.nome = nome;
	}
	
	private HttpStatus validaNome(String nome) {
		if(nome.length() < 5 || nome.length() > 80) return HttpStatus.BAD_REQUEST;
		return HttpStatus.OK;
	}

	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		if(validaCpf(cpf) == HttpStatus.OK) this.cpf = cpf;
	}
	
	private HttpStatus validaCpf(String cpf) {
		if(cpf.length() != 11) return HttpStatus.BAD_REQUEST;
		return HttpStatus.OK;
	}

	public String getNascimento() {
		return nascimento;
	}
	
	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
