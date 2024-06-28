package br.com.trader.esportivo.entradas.domain.repository;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.trader.esportivo.entradas.domain.model.Time;

public interface TimeRepository extends JpaRepository<Time, Long>, JpaSpecificationExecutor<Time> {

	@Query("SELECT MAX(dataAtualizacao) FROM Time")
	Optional<OffsetDateTime> findLastUpdate();

}
