package com.cursomc.dto;

import java.io.Serializable;

import com.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	Integer id;
	String name;
	Double preco;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.name = produto.getNome();
		this.preco = produto.getPreco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
  
}
