package com.github.jeff1Six.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jeff1Six.dominio.Cliente;
import com.github.jeff1Six.dominio.Pedido;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	
	@org.springframework.transaction.annotation.Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
