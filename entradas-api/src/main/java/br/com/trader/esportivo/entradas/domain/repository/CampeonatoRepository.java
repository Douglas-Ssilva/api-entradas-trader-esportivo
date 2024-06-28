package br.com.trader.esportivo.entradas.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.trader.esportivo.entradas.domain.model.Campeonato;

//@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long>, JpaSpecificationExecutor<Campeonato>{

	@Query("SELECT MAX(dataAtualizacao) FROM Campeonato")
	Optional<OffsetDateTime> findLastUpdate();

}
