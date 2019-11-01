package br.com.alura.forum.controller;

import java.net.URI;
//import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.forms.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.forms.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/topicos") // nao preciso repetir essa informacao em todos os metodos, mas todos utilizam o
							// valor "/topicos", mudando o tipo de metodo para post ou get, por exemplo
public class TopicoController {

	// banco - injecao de dependencia
	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a lista de foruns (com nome parametrizado)"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping(produces = "application/json") // verbo http - get
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
	 * @RequestMapping("/topicos")
	 * public List<TopicoDTO> lista() {
	 * Topico t = new Topico("Titulo", "msg", new Curso("curso nome", "categoria"));
	 * 
	 * return TopicoDTO.converter(Arrays.asList(t, t, t)); }
	 */

	@PostMapping(produces="application/json") 
	@Transactional
	// o requestbody informa ao spring que o parametro vem do corpo da requisiçao, e nao da URL
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Validated @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
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
	
	@GetMapping("/{id}") //o spring por padrão pega o nome da variavel do parametro do metodo da classe
						//e o parametro dinamico do metodo get http, nesse caso o "id"
						//se quiser renomear o parametro do metodo da classe diferente do parametro dinamico
						//basta associar com o @PathVariable("id")
	public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));			
		} else {
			//o build monta o corpo do metodo
			return ResponseEntity.notFound().build();
		}
	}
	
	//swagger
	//com o atributo consumes é possível especificar o tipo de conteúdo que ele consome
	//tipo do conteúdo que ele produz com o atributo produces
	@PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Validated @Valid AtualizacaoTopicoForm form){
		
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if (topico.isPresent()) {
			//teoricamente, pela transacao aberta, nao eh necessario atualizar com metodo, o hibernate faz isso junto ao spring
			//porem só funciona sem o save se vc colocar a anotacao @Transactional do javax no metodo
			//mas é uma boa pratica colocar o @Transactional em todos os metodos de transacao, como update e insert
			//topicoRepository.save(topico); 
			Topico topicoNew = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topicoNew));			
		} else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	@ApiOperation("Deleta um topico pelo ID") //padrao
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			topicoRepository.deleteById(id);	
			return ResponseEntity.ok().build();	
		} else {
			return ResponseEntity.notFound().build();			
		}
	}
}
