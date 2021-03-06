package io.github.educontessi.domain.model;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * Classe de teste usada para verificar as mensagens do Bean Validation
 * 
 * @author Eduardo Possamai Contessi
 *
 */
public class VerificaMensagemBeanValidation<T> {

	public boolean verificaMensagem(Set<ConstraintViolation<T>> violacoes, String mensagemComparar) {
		boolean retorno = false;
		for (ConstraintViolation<T> violacao : violacoes) {
			if (getMensagemViolacao(violacao).equals(mensagemComparar)) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}

	private String getMensagemViolacao(ConstraintViolation<T> violacao) {
		System.out.println(violacao.getMessage().replace("{0}", violacao.getPropertyPath().toString()));
		return violacao.getMessage().replace("{0}", violacao.getPropertyPath().toString());
	}

}
