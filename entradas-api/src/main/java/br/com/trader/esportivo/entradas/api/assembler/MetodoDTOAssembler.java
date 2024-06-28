package br.com.trader.esportivo.entradas.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.MetodoDTO;
import br.com.trader.esportivo.entradas.domain.model.Metodo;

@Component
public class MetodoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public MetodoDTO toModel(Metodo metodo) {
		return this.modelMapper.map(metodo, MetodoDTO.class);
	}

	public List<MetodoDTO> toCollectionModel(List<Metodo> metodos) {
		return metodos.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
