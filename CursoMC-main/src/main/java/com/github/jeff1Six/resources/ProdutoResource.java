package com.github.jeff1Six.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jeff1Six.dominio.Produto;
import com.github.jeff1Six.dto.ProdutoDTO;
import com.github.jeff1Six.resources.utils.URL;
import com.github.jeff1Six.services.ProdutoSerivce;


@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoSerivce service;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id){
		
	Produto obj = service.find(id);
	
	return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome",defaultValue="0") String nome, 
			@RequestParam(value="categorias",defaultValue="0") String categorias, 
			@RequestParam(value="page",defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			@RequestParam(value="direction",defaultValue="ASC") String direction){	
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto>list = service.search(nomeDecoded,ids,page, linesPerPage,orderBy, direction);
		Page<ProdutoDTO> listDto = list
			.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity
			.ok()
			.body(listDto);
	}
	 
}