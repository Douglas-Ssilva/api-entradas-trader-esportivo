package br.com.trader.esportivo.entradas.api.disassembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.input.CampeonatoInputDTO;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;

@Component
public class CampeonatoInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Campeonato toDomainObject(CampeonatoInputDTO dto) {
		return modelMapper.map(dto, Campeonato.class);
	}
	
	public void copyProperties(@Valid CampeonatoInputDTO campeonatoInputDTO, Campeonato campeonato) {
		modelMapper.map(campeonatoInputDTO, campeonato);
	}

}
