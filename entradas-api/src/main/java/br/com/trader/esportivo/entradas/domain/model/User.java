package br.com.trader.esportivo.entradas.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "USER")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable { //Serializable por causa do SerializationUtils#clone

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 4, max = 250)
	private String nome;
	
	@NotBlank
	@Size(min = 4, max = 100)
	private String senha;
	
	@NotBlank
	@Email
	@Size(max = 250)
	private String email;

}
