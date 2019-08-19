package com.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.domain.Categoria;
import com.cursomc.domain.Cidade;
import com.cursomc.domain.Estado;
import com.cursomc.domain.Produto;
import com.cursomc.repository.CategoriaRepository;
import com.cursomc.repository.CidadeRepository;
import com.cursomc.repository.EstadoRepository;
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
	}
 
}
