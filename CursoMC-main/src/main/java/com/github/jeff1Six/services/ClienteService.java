package com.github.jeff1Six.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.jeff1Six.dominio.Cidade;
import com.github.jeff1Six.dominio.Cliente;
import com.github.jeff1Six.dominio.Endereco;
import com.github.jeff1Six.dominio.enums.Perfil;
import com.github.jeff1Six.dominio.enums.TipoCliente;
import com.github.jeff1Six.dto.ClienteDTO;
import com.github.jeff1Six.dto.ClienteNewDTO;
import com.github.jeff1Six.repositories.CidadeRepository;
import com.github.jeff1Six.repositories.ClienteRepository;
import com.github.jeff1Six.repositories.EnderecoRepository;
import com.github.jeff1Six.security.UserSS;
import com.github.jeff1Six.services.excpetion.AuthorizationException;
import com.github.jeff1Six.services.excpetion.DataIntegrityException;
import com.github.jeff1Six.services.excpetion.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if(user ==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id:" + id + ", Tipo:" + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao e possivel excluir porque a entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
			throw new AuthorizationException("Acesso Negado");
		}
		Cliente obj = repo.findByEmail(email);
		if(obj == null) {
			throw new ObjectNotFoundException(
					"Objeto Nao encontrado " + 
							user.getId() + 
							" , Tipo: " + 
							Cliente.class.getName());
		}
		
		return obj;
	}

	
	
	
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	@Transactional
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		
		Cliente cli = new Cliente(
				null, objDTO.getNome(),
				objDTO.getEmail(),
				objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()),
				pe.encode(objDTO.getSenha())
				);
		Cidade cid = cidadeRepository.findById(objDTO.getCidadeId()).get();
		
		Endereco end = new Endereco(
				null, objDTO.getLogradouro(), 
				objDTO.getNumero(), 
				objDTO.getComplemento(), 
				objDTO.getBairro(), 
				objDTO.getBairro(), 
				cli, 
				cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() !=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() !=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
		
		
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	
	

}
