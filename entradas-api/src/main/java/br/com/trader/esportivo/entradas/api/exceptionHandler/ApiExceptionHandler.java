package br.com.trader.esportivo.entradas.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.trader.esportivo.entradas.domain.exception.EntityNotFoundException;
import br.com.trader.esportivo.entradas.domain.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	public static final String MSG_USER_GENERIC = "Ocorreu um erro inesperado. Tente novamente e caso o problema persista contacte o administrador do sistema.";

	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handlerEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var problem = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handlerNegocioExceptionException(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		var problem = createProblemBuilder(status, ProblemType.ERROR_BUSINESS, ex.getMessage()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Realiza tratamento dos campos obrigatórios
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleValidateInternal(ex, ex.getBindingResult(), headers, status, request);
	}
	
	/**
	 * Quando user passa tipo de dado incorreto 
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		var problem = createProblemBuilder(status, ProblemType.PAYLOAD_INCOMPREHENSIBLE, "Payload is not valid. Verify possible errors of sintaxe")
				.build();//message exposes details internal application
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	/**
	 * Tratamento para qualquer exception não tratada
	 */
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problem = createProblemBuilder(status, ProblemType.SYSTEM_ERROR, ex.getMessage()).build();
	    log.error(ex.getMessage(), ex);
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException rootCause, HttpHeaders headers, HttpStatus status, WebRequest request) {

		var fieldName = joinPath(rootCause.getPath());
		var value = (String) rootCause.getValue();
		var property = rootCause.getTargetType().getSimpleName();
		
		var detail = String.format("The propertie '%s' was assign '%s' that is not valid. Correct and inform a valid value with type '%s' ", fieldName, value, property);
		var problem = createProblemBuilder(status, ProblemType.PAYLOAD_INCOMPREHENSIBLE, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleValidateInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
	    		.map(objectError -> {  
	    			var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	    			var name = objectError.getObjectName();
	    			
	    			if (objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
	    			
	    			return Problem.Object.builder()
	    				.name(name)
	    				.userMessage(message)
	    				.build();
	    		})
	    		.collect(Collectors.toList());
		
	    var problem = createProblemBuilder(status, ProblemType.INVALID_DATAS, ProblemType.INVALID_DATAS.getTitle())
	    		.objects(problemObjects)
	    		.build();
		
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
	    return references
	    		.stream()
	    		.map(ref -> ref.getFieldName())
	    		.collect(Collectors.joining("."));
	} 
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
		return Problem.builder()
				.status(status.value())
				.type(problemType.getType())
				.title(problemType.getTitle())
				.detail(detail)
				.timestamp(OffsetDateTime.now());
	}
}
