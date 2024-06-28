package br.com.trader.esportivo.entradas.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.BancaDTO;
import br.com.trader.esportivo.entradas.domain.model.Banca;

@Component
public class BancaDTOAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public BancaDTO toModel(Banca banca) {
		return this.mapper.map(banca, BancaDTO.class);
	}
	
	public List<BancaDTO> toCollectionModel(List<Banca> bancas) {
		return bancas.stream()
					.map(this::toModel)
					.collect(Collectors.toList());
	}

}
