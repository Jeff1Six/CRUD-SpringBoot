package com.github.jeff1Six.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.github.jeff1Six.dominio.Categoria;
import com.github.jeff1Six.dominio.Produto;
import com.github.jeff1Six.repositories.CategoriaRepository;
import com.github.jeff1Six.repositories.ProdutoRepository;
import com.github.jeff1Six.services.excpetion.ObjectNotFoundException;



@Service
public class ProdutoSerivce {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository repoCate;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id:" + id + ", Tipo:" + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repoCate.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}
