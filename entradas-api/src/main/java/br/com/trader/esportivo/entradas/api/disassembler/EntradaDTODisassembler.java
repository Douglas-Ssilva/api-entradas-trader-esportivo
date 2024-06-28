package br.com.trader.esportivo.entradas.api.disassembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.input.EntradaInputDTO;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Entrada;
import br.com.trader.esportivo.entradas.domain.model.Metodo;

@Component
public class EntradaDTODisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public List<Entrada> toDomainObject(List<EntradaInputDTO> dtos) {
		return dtos.stream()
				.map(dto -> this.mapper.map(dto, Entrada.class))
				.collect(Collectors.toList());
	}

	public void copyProperties(EntradaInputDTO dto, Entrada entrada) {
		entrada.setCampeonato(new Campeonato());
		entrada.setMetodo(new Metodo());
		this.mapper.map(dto, entrada);
	}

}
