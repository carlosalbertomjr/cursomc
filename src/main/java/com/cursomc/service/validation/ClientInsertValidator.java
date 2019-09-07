package com.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.resources.exceptions.FieldMessage;
import com.cursomc.service.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		// inclui os testes aqui inserindo os erros na lista
		if ((TipoCliente.toEnum(objDto.getTipo()) == TipoCliente.PESSOAFISICA) && (!BR.isValidCPF(objDto.getCpfOuCnpj()))) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		} 
		if ((TipoCliente.toEnum(objDto.getTipo()) == TipoCliente.PESSOAJURIDICA) && (!BR.isValidCNPJ(objDto.getCpfOuCnpj()))){ 
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
			.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
