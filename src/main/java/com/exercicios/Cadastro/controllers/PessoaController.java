package com.exercicios.Cadastro.controllers;

import java.io.IOException;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exercicios.Cadastro.controllers.form.IncluirPessoaCadastro;
import com.exercicios.Cadastro.services.PessoaService;

@Controller
@RequestMapping("/cadastro")
public class PessoaController {
    
	private final PessoaService pessoaService;
	
	public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/lista")
	public String listaTudo(Model model) {
		model.addAttribute("listaTudo", pessoaService.listaTodos());
		return "lista_todos";
	}
	
	@GetMapping("/incluir")
	public String formCadastro() {
		return "cadastro";
	}
	
	@PostMapping("/incluir/salvar")
    public String cadastrarPessoa(IncluirPessoaCadastro incluirPessoaCadastro) throws IOException{
		pessoaService.armazenar(incluirPessoaCadastro);

    	return "redirect:/cadastro/lista";
    }
	
    @GetMapping("/aniversario")
    public String listaAniversario(Model model){
    	model.addAttribute("listaAniversario", pessoaService.listaAniversario());
    	return "lista_aniversario";
    }
    
    @GetMapping("/cpf/{cpf}")
    public String listaCpf(@PathVariable("cpf") String cpf, Model model){
    	model.addAttribute("listaCpf", pessoaService.listaCpf(cpf));
    	return "lista_cpf";
    }    
    
    @GetMapping("/delete/{id}")
    public String removerPessoa(@PathVariable("id") Long id){
    	pessoaService.removerId(id);
    	return "redirect:/cadastro/lista";
    }    
}