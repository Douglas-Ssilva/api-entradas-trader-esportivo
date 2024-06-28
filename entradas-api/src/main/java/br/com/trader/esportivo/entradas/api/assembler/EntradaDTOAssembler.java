package br.com.trader.esportivo.entradas.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.EntradaDTO;
import br.com.trader.esportivo.entradas.domain.model.Entrada;

@Component
public class EntradaDTOAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public EntradaDTO toModel(Entrada entrada) {
		return this.mapper.map(entrada, EntradaDTO.class);
	}

	public List<EntradaDTO> toCollectionModel(List<Entrada> entradas) {
		return entradas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

}
