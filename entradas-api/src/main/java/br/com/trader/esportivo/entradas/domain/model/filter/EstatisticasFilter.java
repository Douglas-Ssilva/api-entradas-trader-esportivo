package br.com.trader.esportivo.entradas.domain.model.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstatisticasFilter {
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataInicio;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataFim;

}
