package br.com.trader.esportivo.entradas.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.trader.esportivo.entradas.api.model.input.NomeInputDTO;

/**
 * Validação de duas propriedades
 */

public class NomesValidator implements ConstraintValidator<NomesValidation, NomeInputDTO>{

	@Override
	public boolean isValid(NomeInputDTO nomeInputDTO, ConstraintValidatorContext context) {
		return StringUtils.isNotBlank(nomeInputDTO.getNome()) || CollectionUtils.isNotEmpty(nomeInputDTO.getNomesTimes());
	}
	
}
