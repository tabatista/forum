package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
	
	//se procuramos assim, funciona sozinho... sem passar a query/JPQL, pois a entidade Topico possui os atribuots titulo e mensagem
	List<Topico> findByTitulo(String titulo);
	List<Topico> findByMensagem(String mensagem);
	
	//para procurarmos pelo atributo do relacionamento... nesse caso o topico tem um curso
	List<Topico> findByCursoNome(String nomeCurso); //não é LIKE	
	//caso exista um atributo no topico chamado "nomeCurso", para evitar ambuiguidade, basta criar o metodo com o nome findByCurso_Nome

	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso") //JPQL
	List<Topico> carregarNomeCurso(@Param("nomeCurso") String nomeCurso);//sem nomenclatura do Spring
}
