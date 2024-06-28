package br.com.trader.esportivo.entradas.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EntradasFlagsValidator.class})
public @interface EntradaPossuiFlags {
	
	String message() default "Uma das flags aposta a favor do mandante ou visitante deve ser marcada.";
	
	Class<?>[] groups() default { };//Usado para grupos de validação

	Class<? extends Payload>[] payload() default { };//Usado para passarmos metadados
	
}
