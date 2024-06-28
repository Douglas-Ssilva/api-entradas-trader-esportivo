package br.com.trader.esportivo.entradas.api;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.trader.esportivo.entradas.api.controller.CampeonatoController;
import br.com.trader.esportivo.entradas.api.controller.MetodoController;
import br.com.trader.esportivo.entradas.api.controller.TimeController;

/**
 * Reponsável apenas por montar links
 * @author dougl
 *
 */
@Component
public class AlgaLinks {
	
	public Link linkToCampeonatos(String rel) {
		return WebMvcLinkBuilder.linkTo(CampeonatoController.class).withRel(rel);
	}
	
	public Link linkToMetodos(String rel) {
		return WebMvcLinkBuilder.linkTo(MetodoController.class).withRel(rel);
	}

	public Link linkToCampeonatos() {
		return linkToCampeonatos(IanaLinkRelations.SELF.value());
	}

	public Link linkToTimes(String rel) {
		return WebMvcLinkBuilder.linkTo(TimeController.class).withRel(rel);
	}
	
	public Link linkToTimes() {
		return linkToTimes(IanaLinkRelations.SELF.value());
	}

	public Link linkToMetodos() {
		return linkToMetodos(IanaLinkRelations.SELF.value());
	}
}
