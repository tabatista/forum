package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe que trata as mensagens das requisicoes de todos os controllers da
 * nossa API
 * 
 * O handler é o interceptador que o spring chama, e aqui configuramos ele
 * @author Thais
 *
 */
@RestControllerAdvice
public class ErroValidacaoHandler {

	// o spring ajuda com as mensagens de erro, considerando que existem diversos idiomas
	@Autowired // injeta a dependencia
	private MessageSource messageSource;

	// metodo que sera chamado quando houver alguma excecao dentro de algum restController
	// excecoes aqui no caso se aplicam a metodos com o @Validated parametrizado
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) //informa o codigo http, o default é 200
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroFormularioDTO> handle(MethodArgumentNotValidException exception) {
		List<ErroFormularioDTO> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroFormularioDTO erro = new ErroFormularioDTO(e.getField(), mensagem);
			dto.add(erro);
		});
		return dto;
	}

}
