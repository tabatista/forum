package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;

@RestController
public class TopicoController {

	@RequestMapping("/topicos")
	public List<TopicoDTO> lista() {
		Topico t = new Topico("Titulo", "msg", new Curso("curso nome", "categoria"));

		return TopicoDTO.converter(Arrays.asList(t, t, t));
	}
}
