package com.github.jeff1Six.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jeff1Six.dominio.Estado;


@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	@Transactional
	public List<Estado> findAllByOrderByNome();
}
