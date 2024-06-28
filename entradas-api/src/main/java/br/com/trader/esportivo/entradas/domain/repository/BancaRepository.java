package br.com.trader.esportivo.entradas.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import br.com.trader.esportivo.entradas.domain.model.Banca;

public interface BancaRepository extends JpaRepository<Banca, Long>, JpaSpecificationExecutor<Banca> {

	Optional<Banca> findByIdAndUserId(Long bancaId, Long userId);

	@Query("SELECT COUNT(b) > 0 FROM Banca b WHERE b.principal = 1 and b.user.id = :userId and (:bancaId is null or b.id <> :bancaId)")//is null por causa do post
	boolean existeBancaPrincipalSalva(Long userId, Long bancaId);

	@Query("SELECT b FROM Banca b LEFT JOIN FETCH b.saqueAporte WHERE b.id = :bancaId  ")
	Optional<Banca> findByIdFetchValores(Long bancaId);

	Banca findByPrincipalTrueAndUserId(Long userId);
	
}
