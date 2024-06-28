package br.com.trader.esportivo.entradas.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancaInputDTO {

	@NotBlank
	@Size(min = 3, max = 100)
	private String nome;
	
	@NotNull
	@DecimalMin(value = "0.0")
	private BigDecimal valor;
	
	private String principal;
	
	@DecimalMin(value = "0.0")
	private BigDecimal saque;
	
	@DecimalMin(value = "0.0")
	private BigDecimal aporte;
	
}
