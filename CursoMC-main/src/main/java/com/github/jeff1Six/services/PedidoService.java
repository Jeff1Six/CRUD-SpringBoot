package com.github.jeff1Six.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.github.jeff1Six.dominio.Cliente;
import com.github.jeff1Six.dominio.ItemPedido;
import com.github.jeff1Six.dominio.PagamentoComBoleto;
import com.github.jeff1Six.dominio.Pedido;
import com.github.jeff1Six.dominio.enums.EstadoPagamento;
import com.github.jeff1Six.repositories.ItemPedidoRepository;
import com.github.jeff1Six.repositories.PagamentoRepository;
import com.github.jeff1Six.repositories.PedidoRepository;
import com.github.jeff1Six.security.UserSS;
import com.github.jeff1Six.services.excpetion.AuthorizationException;
import com.github.jeff1Six.services.excpetion.ObjectNotFoundException;



@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired 
	private boletoService bol;
	
	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private ProdutoSerivce prodServ;
	
	@Autowired
	private ItemPedidoRepository itemRepo;
	
	@Autowired
	private ClienteService clienteService;
	
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id:" + id + ", Tipo:" + Pedido.class.getName()));
	}


	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstant(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			bol.preencherPagamentoComBoleto(pagto, obj.getInstant());
		}
		
		obj = repo.save(obj);
		pagRepo.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(prodServ.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
			
		}
		itemRepo.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}	

	
}
