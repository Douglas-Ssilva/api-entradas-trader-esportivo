package br.com.trader.esportivo.entradas.api.helper;

import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EntradaUtils {
	
	private EntradaUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static BigDecimal getPercent(BigDecimal valor, BigDecimal valorDivide) {
		return nonNull(valor) && nonNull(valorDivide) ? valor.multiply(new BigDecimal("100")).divide(valorDivide, RoundingMode.HALF_UP) : null;
	}

}
