package com.exercicios.Cadastro.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.exercicios.Cadastro.controllers.form.IncluirPessoaCadastro;
import com.exercicios.Cadastro.controllers.form.IncluirPessoaResponse;
import com.exercicios.Cadastro.models.Pessoa;
import com.exercicios.Cadastro.services.PessoaService;

@RestController
@RequestMapping("/cadastro")
public class PessoaController {
    
	private final PessoaService pessoaService;
	
	public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

	@GetMapping()
	public ResponseEntity<List<Pessoa>> listar() {
        return new ResponseEntity<>(pessoaService.listaTodos(), HttpStatus.OK);
    }
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView page = new ModelAndView("index");
		return page;
	}
	
    @GetMapping("/aniversario")
    public ResponseEntity<?> listaAniversario(){
		if(pessoaService.listaAniversario().size() == 0) return new ResponseEntity<>("Erro ao encontrar usuário. Tente novamente.", HttpStatus.NOT_ACCEPTABLE);
    	return new ResponseEntity<>(pessoaService.listaAniversario(), HttpStatus.OK);
    }

    @GetMapping("{cpf}")
    public ResponseEntity<?> listaCpf(@PathVariable("cpf") String cpf){
    	if(pessoaService.listaCpf(cpf).size() == 0) return new ResponseEntity<>("Erro ao encontrar usuário. Tente novamente.", HttpStatus.NOT_ACCEPTABLE);
    	return new ResponseEntity<>(pessoaService.listaCpf(cpf), HttpStatus.OK);
    }

	@GetMapping("/incluir")
	public ModelAndView incluir() {
		ModelAndView page = new ModelAndView("cadastro");
		return page;
	}
    
    @PostMapping("/incluir")
    public ModelAndView cadastrar(@ModelAttribute IncluirPessoaCadastro incluirPessoaCadastro) throws IOException{  	
    	if(pessoaService.validaDados(incluirPessoaCadastro) == HttpStatus.BAD_REQUEST) {
    		//return new ResponseEntity<>("Erro ao cadastrar usuário. Algum campo está Incorreto, tente novamente.", HttpStatus.BAD_REQUEST);
    	}
    	
    	var pessoa = pessoaService.armazenar(incluirPessoaCadastro);
    	
    	var pessoaResponse = new IncluirPessoaResponse();
    	BeanUtils.copyProperties(pessoa, pessoaResponse);

		ModelAndView page = new ModelAndView("index");
    	return page;
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<?> remover(@PathVariable("id") Long id){
    	pessoaService.removerId(id);
    	return new ResponseEntity<>(HttpStatus.OK);
    }    
}