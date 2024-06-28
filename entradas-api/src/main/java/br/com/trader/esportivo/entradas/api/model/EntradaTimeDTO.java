package br.com.trader.esportivo.entradas.api.model;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntradaTimeDTO {
	
	private String nomeTime;
	
	private Long timeId;

	private Long metodoId;
	
	private BigDecimal valor;
	
	private Long totalEntradas;

	@Override
	public int hashCode() {
		return Objects.hash(metodoId, timeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntradaTimeDTO other = (EntradaTimeDTO) obj;
		return Objects.equals(metodoId, other.metodoId) && Objects.equals(timeId, other.timeId);
	}

}
