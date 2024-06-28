package br.com.trader.esportivo.entradas.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.AlgaLinks;
import br.com.trader.esportivo.entradas.api.controller.TimeController;
import br.com.trader.esportivo.entradas.api.model.TimeDTO;
import br.com.trader.esportivo.entradas.domain.model.Time;

@Component
public class TimeDTOAssembler extends RepresentationModelAssemblerSupport<Time, TimeDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public TimeDTOAssembler() {
		super(TimeController.class, TimeDTO.class);
	}
	
	@Override
	public TimeDTO toModel(Time time) {
		var timeDTO = createModelWithId(time.getId(), time);
		this.modelMapper.map(time, timeDTO);
		timeDTO.add(algaLinks.linkToTimes("times"));
		return timeDTO;
	}
	
	@Override
	public CollectionModel<TimeDTO> toCollectionModel(Iterable<? extends Time> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToTimes());
	}

}
