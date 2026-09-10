package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MercadoRepository extends JpaRepository<Mercado, Long>, JpaSpecificationExecutor<Mercado> {
    Optional<Mercado> findByCepAndNumero(String cep, Integer numero);
}
