package br.com.trader.esportivo.entradas.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(value = Include.NON_NULL)
@Getter
@Builder
/**
 * Problem Details for HTTP APIs
 * 
 */
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	//specialization
	private String userMessage;
	private OffsetDateTime timestamp;
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object {
		private String name;
		
		private String userMessage;
		
	}
	

}
