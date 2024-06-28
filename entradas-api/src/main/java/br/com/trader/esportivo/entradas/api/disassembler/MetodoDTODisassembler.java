package br.com.trader.esportivo.entradas.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.input.MetodoInputDTO;
import br.com.trader.esportivo.entradas.domain.model.Metodo;

@Component
public class MetodoDTODisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Metodo toDomainObject(MetodoInputDTO dto) {
		return this.mapper.map(dto, Metodo.class);
	}

	public void copyProperties(MetodoInputDTO dto, Metodo metodo) {
		this.mapper.map(dto, metodo);
	}

}
