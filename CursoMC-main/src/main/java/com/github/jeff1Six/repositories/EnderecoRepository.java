package com.github.jeff1Six.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.jeff1Six.dominio.Endereco;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
	
	
}
