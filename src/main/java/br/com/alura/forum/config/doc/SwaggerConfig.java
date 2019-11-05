package br.com.alura.forum.config.doc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Acessa pelo
 * http://localhost:8080/swagger-ui.html
 * 
 * conhecimento: https://www.treinaweb.com.br/blog/documentando-uma-api-spring-boot-com-o-swagger/
 *
 * @author tabatista
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	//TODO ver a configuracao de seguranca
	
	/**
	 * O Docket que estamos definindo no nosso bean 
	 * nos permite configurar aspectos dos endpoints expostos por ele.
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
//				.paths(PathSelectors.ant("/topicos/*"))
				.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, responseMessageForGET())
				.apiInfo(apiInfo());
				//.globalResponseMessage(RequestMethod.POST....
	}
	
	//status http
	//https://www.hostinger.com.br/tutoriais/o-que-e-http-error-e-principais-codigos-http/
	@SuppressWarnings("serial")
	private List<ResponseMessage> responseMessageForGET()
	{
	    return new ArrayList<ResponseMessage>() {{
	        add(new ResponseMessageBuilder()
	            .code(500)
	            .message("Erro interno do Servidor")
	            .responseModel(new ModelRef("Error servidor"))
	            .build());
	        add(new ResponseMessageBuilder()
	            .code(200)
	            .message("Ok")
	            .build());
//	        add(new ResponseMessageBuilder()
//	        		.code(201)
//	        		.message("Criado")
//	        		.build());
//	        add(new ResponseMessageBuilder()
//	        		.code(202)
//	        		.message("Aceito")
//	        		.build());
	        add(new ResponseMessageBuilder()
	        		.code(203)
	        		.message("Não autorizado")
	        		.build());
	    }};
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("Forum")
	            .description("Estudando spring boot no alura")
	            .version("1.0.0")
	            .license("Apache License Version 2.0")
	            .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
	            .contact(new Contact("Thais", "site meu", "thais@thais.com.br"))
	            .build();
	}
}
