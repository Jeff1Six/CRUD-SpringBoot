package com.github.jeff1Six.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jeff1Six.dominio.Estado;
import com.github.jeff1Six.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	public EstadoRepository repo;
	
	
	
	public List<Estado> findAll(){
		return repo.findAllByOrderByNome();
	}
}
