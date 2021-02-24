package com.github.jeff1Six.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.jeff1Six.dominio.Categoria;
import com.github.jeff1Six.dominio.Cidade;
import com.github.jeff1Six.dominio.Cliente;
import com.github.jeff1Six.dominio.Endereco;
import com.github.jeff1Six.dominio.Estado;
import com.github.jeff1Six.dominio.ItemPedido;
import com.github.jeff1Six.dominio.Pagamento;
import com.github.jeff1Six.dominio.PagamentoComBoleto;
import com.github.jeff1Six.dominio.PagamentoComCartao;
import com.github.jeff1Six.dominio.Pedido;
import com.github.jeff1Six.dominio.Produto;
import com.github.jeff1Six.dominio.enums.EstadoPagamento;
import com.github.jeff1Six.dominio.enums.Perfil;
import com.github.jeff1Six.dominio.enums.TipoCliente;
import com.github.jeff1Six.repositories.CategoriaRepository;
import com.github.jeff1Six.repositories.CidadeRepository;
import com.github.jeff1Six.repositories.ClienteRepository;
import com.github.jeff1Six.repositories.EnderecoRepository;
import com.github.jeff1Six.repositories.EstadoRepository;
import com.github.jeff1Six.repositories.ItemPedidoRepository;
import com.github.jeff1Six.repositories.PagamentoRepository;
import com.github.jeff1Six.repositories.PedidoRepository;
import com.github.jeff1Six.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository cateRepo;

	@Autowired
	private ProdutoRepository prodRepo;

	@Autowired
	private EstadoRepository estRepo;

	@Autowired
	private CidadeRepository cidRepo;

	@Autowired
	private ClienteRepository cliRepo;

	@Autowired
	private EnderecoRepository endRepo;

	@Autowired
	private PedidoRepository pedRepo;

	@Autowired
	private PagamentoRepository pagRepo;
	
	@Autowired
	private ItemPedidoRepository itemRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instatiateTestDatabase() throws ParseException {
				
			Categoria cat1 = new Categoria(null, "Informatica");
			Categoria cat2 = new Categoria(null, "Escritorio");
			Categoria cat3 = new Categoria(null, "Cama/Mesa");
			Categoria cat4 = new Categoria(null, "Banheiro");
			Categoria cat5 = new Categoria(null, "Perfumaria");
			Categoria cat6 = new Categoria(null, "Comida");
			Categoria cat7 = new Categoria(null, "Festa");
			Categoria cat8 = new Categoria(null, "Teste");

			
			
			Produto p1 = new Produto(null, "Computador", 2000.00);
			Produto p2 = new Produto(null, "Impressora", 800.00);
			Produto p3 = new Produto(null, "Mouse", 80.00);
			Produto p4 = new Produto(null, "Mesa de escritorio", 300.00);
			Produto p5 = new Produto(null, "Toalha", 30.00);
			Produto p6 = new Produto(null, "Colcha", 200.00);
			Produto p8 = new Produto(null, "Rocadeira", 800.00);
			Produto p9 = new Produto(null, "Abajour", 100.00);
			Produto p10 = new Produto(null, "Pendente", 110.00);
			Produto p11 = new Produto(null, "Shampoo", 10.00);


			Estado est1 = new Estado(null, "Minas Gerais");
			Estado est2 = new Estado(null, "Sao Paulo");

			Cidade c1 = new Cidade(null, "Uberlandia", est1);
			Cidade c2 = new Cidade(null, "Sao Paulo", est2);
			Cidade c3 = new Cidade(null, "Campinas", est2);
			
			Cliente cli1 = new Cliente (null, "Maria a", "jefferson2001santos@gmail.com" ,"24157855043", TipoCliente.PESSOAFISICA, pe.encode("123"));
			Cliente cli2 = new Cliente (null, "Ana Costa a", "jeffersonsantos@gmail.com" ,"93656967040", TipoCliente.PESSOAFISICA, pe.encode("123"));
			cli2.addPerfil(Perfil.ADMIN);
			cli1.getTelefones().addAll(Arrays.asList("123456789","9876544321"));
			cli2.getTelefones().addAll(Arrays.asList("123456789","9876544321"));

			
			Endereco end1 = new Endereco(null, "RUa flores", "300", "Apto300", "Jardim", "3822039", cli1, c1);
			Endereco end2 = new Endereco(null, "RUa Abc", "231", "Rua azul", "Jardim", "1231553", cli1, c2);
			Endereco end3 = new Endereco(null, "RUa Abaaaac", "23111", null, "Centro", "1231553", cli2, c2);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32") , cli1, end1);
			Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:31") , cli1, end2);

			Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
			ped1.setPagamento(pgto1);
			
			Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
			ped2.setPagamento(pgto2);
			
			ItemPedido pedido1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00); 
			ItemPedido pedido2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00); 
			ItemPedido pedido3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00); 

			ped1.getItens().addAll(Arrays.asList(pedido1,pedido2));
			ped2.getItens().addAll(Arrays.asList(pedido3));

			p1.getItens().addAll(Arrays.asList(pedido1));
			p2.getItens().addAll(Arrays.asList(pedido3));
			p3.getItens().addAll(Arrays.asList(pedido2));

			
			cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
			cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
			
			cli1.getEnderecos().addAll(Arrays.asList(end3));

			
			est1.getCidades().addAll(Arrays.asList(c1));
			est2.getCidades().addAll(Arrays.asList(c2,c3));

			cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
			cat2.getProdutos().addAll(Arrays.asList(p2,p4));
			cat3.getProdutos().addAll(Arrays.asList(p5,p6));
			cat5.getProdutos().addAll(Arrays.asList(p1,p2,p3,p8));
			cat6.getProdutos().addAll(Arrays.asList(p9));
			cat7.getProdutos().addAll(Arrays.asList(p9,p10));


			p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
			p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
			p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
			p4.getCategorias().addAll(Arrays.asList(cat2));
			p5.getCategorias().addAll(Arrays.asList(cat3));
			p6.getCategorias().addAll(Arrays.asList(cat3));
			p8.getCategorias().addAll(Arrays.asList(cat5));
			p9.getCategorias().addAll(Arrays.asList(cat6));
			p10.getCategorias().addAll(Arrays.asList(cat6));
			p11.getCategorias().addAll(Arrays.asList(cat7));		

			
			cateRepo.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8));
			prodRepo.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p8,p9,p10,p11));
			estRepo.saveAll(Arrays.asList(est1,est2));
			cidRepo.saveAll(Arrays.asList(c1,c2,c3));
			cliRepo.saveAll(Arrays.asList(cli1,cli2));
			endRepo.saveAll(Arrays.asList(end1,end2));
			pedRepo.saveAll(Arrays.asList(ped1,ped2));
			pagRepo.saveAll(Arrays.asList(pgto1,pgto2));
			itemRepo.saveAll(Arrays.asList(pedido1,pedido2,pedido3));

	}

}
