package br.com.alura.forum.controller;

import java.net.URI;
//import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.forms.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos") // nao preciso repetir essa informacao em todos os metodos, mas todos utilizam o
							// valor "/topicos", mudando o tipo de metodo para post ou get, por exemplo
public class TopicoController {

	// banco - injecao de dependencia
	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping // verbo http - get
	public List<TopicoDTO> lista(String nomeCurso) {
		// com parametro equivale a http://localhost:8080/topicos?nomeCurso=Thais
		System.out.println(nomeCurso);
		List<Topico> topicos = null;
		if (nomeCurso == null) {
			topicos = topicoRepository.findAll();
		} else {
			topicos = topicoRepository.findByCursoNome(nomeCurso);
		}
		return TopicoDTO.converter(topicos);
	}

	/*
	 * @RequestMapping("/topicos") public List<TopicoDTO> lista() { Topico t = new
	 * Topico("Titulo", "msg", new Curso("curso nome", "categoria"));
	 * 
	 * return TopicoDTO.converter(Arrays.asList(t, t, t)); }
	 */

	@PostMapping // o requestbody informa ao spring que o parametro vem do corpo da requisiçao, e nao da URL
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		//com o codigo http 201, é necessario retornar a URI do recurso criado....  cabeçalho e o corpo
		//informamos o caminho do recurso, ou seja, a URL
		//o spring ajuda  a montar isso com o UriComponentsBuilder
		//passando o caminho com chave {} informamos que é um parametro dinamico
		//para passar as informações do parametro, usamos o buildAndExpand
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		//retorna o codigo http 201 - criado
		return ResponseEntity.created(uri).body(new TopicoDTO(topico)); 
	}
}
