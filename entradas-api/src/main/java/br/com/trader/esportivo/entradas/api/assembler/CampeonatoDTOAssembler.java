package br.com.trader.esportivo.entradas.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.AlgaLinks;
import br.com.trader.esportivo.entradas.api.controller.CampeonatoController;
import br.com.trader.esportivo.entradas.api.model.CampeonatoDTO;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;

@Component
public class CampeonatoDTOAssembler extends RepresentationModelAssemblerSupport<Campeonato, CampeonatoDTO> {

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public CampeonatoDTOAssembler() {
		super(CampeonatoController.class, CampeonatoDTO.class);
	}
	
	@Override
	public CampeonatoDTO toModel(Campeonato campeonato) {
		var campeonatoDTO = createModelWithId(campeonato.getId(), campeonato);
		this.modelMapper.map(campeonato, campeonatoDTO);
		campeonatoDTO.add(algaLinks.linkToCampeonatos("campeonatos"));
		return campeonatoDTO;
	}
	
	public CampeonatoDTO toModelSemLinks(Campeonato campeonato) {
		return this.modelMapper.map(campeonato, CampeonatoDTO.class);
	}
	
	@Override
	public CollectionModel<CampeonatoDTO> toCollectionModel(Iterable<? extends Campeonato> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToCampeonatos());
	}
	
	public List<CampeonatoDTO> toCollectionDTOs(List<Campeonato> campeonatos) {
		return campeonatos.stream()
				.map(this::toModelSemLinks)
				.collect(Collectors.toList());
	}
	
}
