package com.github.jeff1Six.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jeff1Six.dominio.Pagamento;


@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
	
	
}
