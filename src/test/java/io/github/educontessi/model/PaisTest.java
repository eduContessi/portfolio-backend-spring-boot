package io.github.educontessi.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Classe de teste para entidade {@link Pais}
 * 
 * @author Eduardo Contessi
 *
 */
public class PaisTest {

	private Validator validator;

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("Deve retornar violações para campos obrigatórios")
	public void deveRetornarViolacoesParaCamposObrigatorios() {
		// Arranjos
		Pais pais = new Pais();

		String mensagem1 = "nome é obrigatório(a)";
		String mensagem2 = "bacen é obrigatório(a)";
		String mensagem3 = "sigla é obrigatório(a)";

		// Execução
		Set<ConstraintViolation<Pais>> violacoes = validator.validate(pais);

		// Resultados
		assertNotNull(violacoes);
		assertFalse(violacoes.isEmpty());
		assertEquals(3, violacoes.size());
		assertTrue(verificaMensagem(violacoes, mensagem1));
		assertTrue(verificaMensagem(violacoes, mensagem2));
		assertTrue(verificaMensagem(violacoes, mensagem3));
	}

	@Test
	@DisplayName("Deve retornar violações para tamanhos mínimos de campos obrigatórios")
	public void deveRetornarViolacoesParaTamanhosMinimosDeCamposObrigatorios() {
		// Arranjos
		Pais pais = new Pais();
		pais.setNome("BR"); // MINIMO 3
		pais.setSigla("B"); // MINIMO 2
		pais.setBacen("B"); // MINIMO 2

		String mensagem1 = "nome deve ter o tamanho entre 3 e 100";
		String mensagem2 = "bacen deve ter o tamanho entre 2 e 10";
		String mensagem3 = "sigla deve ter o tamanho entre 2 e 10";

		// Execução
		Set<ConstraintViolation<Pais>> violacoes = validator.validate(pais);

		// Resultados
		assertNotNull(violacoes);
		assertFalse(violacoes.isEmpty());
		assertEquals(3, violacoes.size());
		assertTrue(verificaMensagem(violacoes, mensagem1));
		assertTrue(verificaMensagem(violacoes, mensagem2));
		assertTrue(verificaMensagem(violacoes, mensagem3));
	}

	@Test
	@DisplayName("Deve retornar violações para tamanhos máximos de campos obrigatórios")
	public void deveRetornarViolacoesParaTamanhosMaximosDeCamposObrigatorios() {
		// Arranjos
		Pais pais = new Pais();
		pais.setNome(
				"BRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRBRB"); // MAXIMO
																															// 100
		pais.setSigla("BRBRBRBRBRB"); // MAXIMO 10
		pais.setBacen("BRBRBRBRBRB"); // MAXIMO 10

		String mensagem1 = "nome deve ter o tamanho entre 3 e 100";
		String mensagem2 = "bacen deve ter o tamanho entre 2 e 10";
		String mensagem3 = "sigla deve ter o tamanho entre 2 e 10";

		// Execução
		Set<ConstraintViolation<Pais>> violacoes = validator.validate(pais);

		// Resultados
		assertNotNull(violacoes);
		assertFalse(violacoes.isEmpty());
		assertEquals(3, violacoes.size());
		assertTrue(verificaMensagem(violacoes, mensagem1));
		assertTrue(verificaMensagem(violacoes, mensagem2));
		assertTrue(verificaMensagem(violacoes, mensagem3));
	}

	private boolean verificaMensagem(Set<ConstraintViolation<Pais>> violacoes, String mensagemComparar) {
		boolean retorno = false;
		for (ConstraintViolation<Pais> violacao : violacoes) {
			if (getMensagemViolacao(violacao).equals(mensagemComparar)) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}

	private String getMensagemViolacao(ConstraintViolation<Pais> violacao) {
		return violacao.getMessage().replace("{0}", violacao.getPropertyPath().toString());
	}
}
