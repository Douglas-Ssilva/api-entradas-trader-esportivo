package br.com.trader.esportivo.entradas.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.input.TimeInputDTO;
import br.com.trader.esportivo.entradas.domain.model.Time;

@Component
public class TimeInputDTODisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Time toDomainObject(TimeInputDTO dto) {
		return this.mapper.map(dto, Time.class);
	}

	public void copyProperties(TimeInputDTO timeDTO, Time time) {
		this.mapper.map(timeDTO, time);
	}

}
