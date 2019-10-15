package br.com.alura.forum.controller;

//import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
public class TopicoController {

	// banco - injecao de dependencia
	@Autowired
	private TopicoRepository topicoRepository;

	@RequestMapping("/topicos")
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
}
