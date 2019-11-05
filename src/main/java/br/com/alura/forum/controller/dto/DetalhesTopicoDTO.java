package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.forum.model.StatusTopico;
import br.com.alura.forum.model.Topico;

/**
 * DTO não retorna classes como o Curso, por exemplo...
 * 
 * @author Pcier
 *
 */
public class DetalhesTopicoDTO {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDTO> respostas;

	public DetalhesTopicoDTO(Topico t) {
		this.id = t.getId();
		this.titulo = t.getTitulo();
		this.mensagem = t.getMensagem();
		this.dataCriacao = t.getDataCriacao();
		this.nomeAutor = t.getAutor().getNome();
		this.status = t.getStatus();
		this.respostas = new ArrayList<>();
		this.respostas.addAll(RespostaDTO.conveter(t.getRespostas()));
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDTO> getRespostas() {
		return respostas;
	}

}
