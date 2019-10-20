package br.com.alura.forum.controller.forms;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;

/**
 * Enquanto o TopicoDTO manda informações, o TopicoForm recebe informações (para
 * cadastro, atualização etc)
 * 
 * @author Pcier
 *
 */
public class TopicoForm {
	private String titulo;
	private String mensagem;
	private String nomeCurso;

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

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public Topico converter(CursoRepository cursoCepository) {
		Curso curso = cursoCepository.findByNome(this.nomeCurso);
		return new Topico(this.titulo, this.mensagem, curso);
	}
}
