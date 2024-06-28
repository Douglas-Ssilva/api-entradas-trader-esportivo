package br.com.trader.esportivo.entradas.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.trader.esportivo.entradas.api.model.CampeonatoDTO;
import br.com.trader.esportivo.entradas.api.model.TimeDTO;
import br.com.trader.esportivo.entradas.domain.model.Campeonato;
import br.com.trader.esportivo.entradas.domain.model.Time;

/**
 * Como o ModelMapper não é um componente do Spring, mas sim uma lib de terceiro, temos que ensinar ao Spring como criar uma instancia desse tipo
 * @author dougl
 *
 */

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Campeonato.class, CampeonatoDTO.class)
		.addMapping(Campeonato::getLabelContinenteString, CampeonatoDTO::setContinente)
		.addMapping(Campeonato::getLabelPaisString, CampeonatoDTO::setPais);
		
		modelMapper.createTypeMap(Time.class, TimeDTO.class)
		.addMapping(Time::getLabelContinenteString, TimeDTO::setContinente)
		.addMapping(Time::getLabelPaisString, TimeDTO::setPais);
		
		return modelMapper;
	}
}
