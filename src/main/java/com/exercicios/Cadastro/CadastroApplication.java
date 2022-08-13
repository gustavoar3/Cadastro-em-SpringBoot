package com.exercicios.Cadastro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.exercicios.Cadastro.models.Pessoa;

@SpringBootApplication
public class CadastroApplication implements CommandLineRunner{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(CadastroApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{	
		//String sql = "Select * From PessoaCadastro";
		//List<Pessoa> pessoa = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pessoa.class));
		
		//pessoa.forEach(System.out :: println);
	}
	
	
}
