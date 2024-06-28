package br.com.trader.esportivo.entradas.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.trader.esportivo.entradas.domain.model.Banca;
import br.com.trader.esportivo.entradas.domain.model.Metodo;

public interface MetodoRepository extends JpaRepository<Metodo, Long>, JpaSpecificationExecutor<Metodo>{

	Optional<Metodo> findByIdAndBancaId(Long metodoId, Long bancaId);

	void deleteByBanca(Banca banca);//Pra cada método disparou um comando

	@Modifying
	@Query("DELETE FROM Metodo WHERE banca = :banca")
	void deleteBanca(@Param("banca") Banca banca);

}
