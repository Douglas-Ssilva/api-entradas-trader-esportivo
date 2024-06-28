package br.com.trader.esportivo.entradas.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.model.input.UserInput;
import br.com.trader.esportivo.entradas.api.model.input.UserInputDTO;
import br.com.trader.esportivo.entradas.domain.model.User;

@Component
public class UserDTODisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public User toDomainObject(UserInputDTO dto) {
		return this.mapper.map(dto, User.class);
	}

	public void copyProperties(UserInput dto, User user) {
		this.mapper.map(dto, user);
	}

}
