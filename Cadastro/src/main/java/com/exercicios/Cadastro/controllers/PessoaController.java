package com.exercicios.Cadastro.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercicios.Cadastro.controllers.form.IncluirPessoaCadastro;
import com.exercicios.Cadastro.controllers.form.IncluirPessoaResponse;
import com.exercicios.Cadastro.models.Pessoa;
import com.exercicios.Cadastro.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/cadastro")
public class PessoaController {
    
	private final PessoaService pessoaService;
	private final ObjectMapper mapper = new ObjectMapper();
	
	public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

	@GetMapping()
	public ResponseEntity<List<Pessoa>> listar() {
        return new ResponseEntity<>(pessoaService.listaTodos(), HttpStatus.OK);
    }
	
    @GetMapping("/aniversario")
    public ResponseEntity<List<String>> listaAniversario(){
        return new ResponseEntity<>(pessoaService.listaAniversario(), HttpStatus.OK);
    }

    @GetMapping("{cpf}")
    public ResponseEntity<List<Pessoa>> listaCpf(@PathVariable("cpf") String cpf){
        return new ResponseEntity<>(pessoaService.listaCpf(cpf), HttpStatus.OK);
    }
    
    @PostMapping("/incluir")
    public ResponseEntity<IncluirPessoaResponse> cadastrar(@RequestParam String pessoaData) throws IOException{
    	final var incluirPessoaCadastro = mapper.readValue(pessoaData, IncluirPessoaCadastro.class);
    	
    	if(pessoaService.validaDados(incluirPessoaCadastro) == HttpStatus.BAD_REQUEST) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	var pessoa = pessoaService.armazenar(incluirPessoaCadastro);
    	
    	var pessoaResponse = new IncluirPessoaResponse();
    	BeanUtils.copyProperties(pessoa, pessoaResponse);
    	return new ResponseEntity<>(pessoaResponse, HttpStatus.CREATED);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id){
    	pessoaService.removerId(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }    
}