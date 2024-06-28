package br.com.trader.esportivo.entradas.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NomesValidator.class})
public @interface NomesValidation {
	
	String message() default "Nome é obrigatório.";
	
	Class<?>[] groups() default { };//Usado para grupos de validação

	Class<? extends Payload>[] payload() default { };//Usado para passarmos metadados
	
}
