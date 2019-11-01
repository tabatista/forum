package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.Topico;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe do tipo DTO manda informações "filtradas"
 * @author Thais
 *
 */
public class TopicoDTO {

	@ApiModelProperty(value = "Codigo do topico")
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public TopicoDTO(Topico t) {
		this.id = t.getId();
		this.titulo = t.getTitulo();
		this.mensagem = t.getMensagem();
		this.dataCriacao = t.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public static List<TopicoDTO> converter(List<Topico> topicos) {
		return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
	}
}
