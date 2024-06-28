package br.com.trader.esportivo.entradas.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
	ERROR_BUSINESS("/error-business", "Violation business rules"),
	PAYLOAD_INCOMPREHENSIBLE("/payload-incomprehensible", "Payload is not comprehensible"),
	ENTITY_NOT_DELETE("/entity-not-delete", "Entidade não pode ser deletada"), 
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"), 
	INVALID_DATAS("/invalid-datas", "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente."), 
	SYSTEM_ERROR("/system-error", "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir entre em contato com o administrador do sistema"), 
	ACCESS_DENIED("/access-denied", "Access denied"), 
	PROPERTY_NOT_VALID("/property-not-valid", "Property not valid");
	
	private String type;
	private String title;
	
	private ProblemType(String path, String title) {
		this.type = "https://entradas-trader.com" + path;
		this.title = title;
	}
	
}
