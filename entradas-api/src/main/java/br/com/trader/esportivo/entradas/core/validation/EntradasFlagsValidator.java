package br.com.trader.esportivo.entradas.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.trader.esportivo.entradas.api.model.input.EntradaInputDTO;

/**
 * Validação de duas propriedades
 */

public class EntradasFlagsValidator implements ConstraintValidator<EntradaPossuiFlags, EntradaInputDTO>{

	@Override
	public boolean isValid(EntradaInputDTO entrada, ConstraintValidatorContext context) {
		return !(Boolean.TRUE.equals(entrada.getApostaAFavorMandante()) && Boolean.TRUE.equals(entrada.getApostaAFavorVisitante())) 
				&& (Boolean.TRUE.equals(entrada.getApostaAFavorMandante()) || Boolean.TRUE.equals(entrada.getApostaAFavorVisitante()));
	}
	
}
