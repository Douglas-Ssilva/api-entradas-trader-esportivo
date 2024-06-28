package br.com.trader.esportivo.entradas.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.input.BancaInputDTO;
import br.com.trader.esportivo.entradas.domain.model.Banca;

@Component
public class BancaDTODisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Banca toDomainObject(BancaInputDTO dto) {
		return this.mapper.map(dto, Banca.class);
	}

	public void copyProperties(BancaInputDTO dto, Banca banca) {
		this.mapper.map(dto, banca);
	}

}
