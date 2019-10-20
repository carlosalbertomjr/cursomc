package com.cursomc.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursomc.domain.Produto;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.dto.ProdutoDTO;
import com.cursomc.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	Logger logger = Logger.getLogger("ProdutoService");

	@Autowired
	ProdutoService service;

	@GetMapping("/{id}")
	public ResponseEntity<Produto> find(@RequestParam Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		List<Integer> listCategorias = Arrays.asList(categorias.split(",")).stream().map(Integer::parseInt)
				.collect(Collectors.toList());
		String nomeDecode;
		try {
			nomeDecode = URLDecoder.decode(nome, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			nomeDecode = "";
		}
		Page<Produto> list = service.search(nomeDecode, listCategorias, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(p -> new ProdutoDTO(p));
		return ResponseEntity.ok().body(listDTO);
	}

}
