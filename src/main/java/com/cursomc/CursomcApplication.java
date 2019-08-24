package com.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.Estado;
import com.cursomc.domain.EstadoPagamento;
import com.cursomc.domain.Pagamento;
import com.cursomc.domain.PagamentoComBoleto;
import com.cursomc.domain.PagamentoComCartao;
import com.cursomc.domain.Pedido;
import com.cursomc.domain.Produto;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.repository.CidadeRepository;
import com.cursomc.repository.ClienteRepository;
import com.cursomc.repository.EnderecoRepository;
import com.cursomc.repository.EstadoRepository;
import com.cursomc.repository.PedidoRepository;
import com.cursomc.repository.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	CategoriaRepository repoCategoria;
	@Autowired
	ProdutoRepository repoProduto;
	@Autowired
	EstadoRepository repoEstado;
	@Autowired
	CidadeRepository repoCidade;
	
	@Autowired
	ClienteRepository repoCliente;
	
	@Autowired
	EnderecoRepository repoEndereco;
	
	@Autowired
	PedidoRepository repoPedido;
	
	@Autowired
	PagamentoRepository repoPagamento;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		repoCategoria.saveAll(Arrays.asList(cat1, cat2));
		repoProduto.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado minasGerais = new Estado(null, "Minas Gerais");
		Estado estadoSaoPaulo = new Estado(null,"São Paulo");
		
		Cidade uberlandia = new Cidade(null, "Uberlância", minasGerais);
		Cidade saoPaulo = new Cidade(null, "São Paulo", estadoSaoPaulo);
		Cidade campinas = new Cidade(null, "Campinas", estadoSaoPaulo);
		
		repoEstado.saveAll(Arrays.asList(minasGerais, estadoSaoPaulo));
		repoCidade.saveAll(Arrays.asList(uberlandia, saoPaulo, campinas));
		
		minasGerais.getCidades().addAll(Arrays.asList(uberlandia));
		estadoSaoPaulo.getCidades().addAll(Arrays.asList(saoPaulo, campinas));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@email.com", "123", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("11111", "22222"));
		Endereco e1 = new Endereco(null, "Rua flores", "300", "Apto 303", "Jardim", "098703498", cli1, uberlandia);
		Endereco e2 = new Endereco(null, "Av. Matos", "105", "Sala 800", "Centro", "13948104", cli1, saoPaulo);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		repoCliente.saveAll(Arrays.asList(cli1));
		repoEndereco.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1, null);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2, null);
		
		System.out.println("Cliente ped1 -> " + ped1.getCliente().getId());
		System.out.println("Cliente ped2 -> " + ped2.getCliente().getId());
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		repoPedido.saveAll(Arrays.asList(ped1, ped2));
		repoPagamento.saveAll(Arrays.asList(pagto1, pagto2));
		
	}
 
}
