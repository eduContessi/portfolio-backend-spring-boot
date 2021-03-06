package io.github.educontessi.domain.helpers.util;

import static io.github.educontessi.domain.helpers.util.FuncoesString.adicionaMascara;
import static io.github.educontessi.domain.helpers.util.FuncoesString.formatarNome;
import static io.github.educontessi.domain.helpers.util.FuncoesString.removeMascaraDeNumeros;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Classe de teste para entidade {@link FuncoesString}
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@SpringBootTest
public class FuncoesStringTest {

	private final String CPF = "34654886524";
	private final String CNPJ = "05702348000144";
	private final String CEP = "88888888";

	@BeforeEach
	public void setUp() {
	}

	@Test
	public void deveAdicionarMascaraCPF() {
		// Arranjos
		String resultadoAtual = null;
		String resultadoEsperado = "346.548.865-24";

		// Execução
		resultadoAtual = adicionaMascara(TipoMascara.CPF, this.CPF);

		// Resultados
		assertNotNull(resultadoAtual);
		assertEquals(resultadoEsperado, resultadoAtual);
	}

	@Test
	public void deveRemoverMascaraCPF() {
		// Arranjos
		String resultadoEsperado = this.CPF;
		String resultadoAtual = "346.548.865-24";

		// Execução
		resultadoAtual = removeMascaraDeNumeros(resultadoAtual);

		// Resultados
		assertNotNull(resultadoAtual);
		assertEquals(resultadoEsperado, resultadoAtual);
	}

	@Test
	public void deveAdicionarMascaraCNPJ() {
		// Arranjos
		String resultadoAtual = null;
		String resultadoEsperado = "05.702.348/0001-44";

		// Execução
		resultadoAtual = adicionaMascara(TipoMascara.CNPJ, this.CNPJ);

		// Resultados
		assertNotNull(resultadoAtual);
		assertEquals(resultadoEsperado, resultadoAtual);
	}

	@Test
	public void deveRemoverMascaraCNPJ() {
		// Arranjos
		String resultadoEsperado = this.CNPJ;
		String resultadoAtual = "05.702.348/0001-44";

		// Execução
		resultadoAtual = removeMascaraDeNumeros(resultadoAtual);

		// Resultados
		assertNotNull(resultadoAtual);
		assertEquals(resultadoEsperado, resultadoAtual);
	}

	@Test
	public void deveAdicionarMascaraCEP() {
		// Arranjos
		String resultadoAtual = null;
		String resultadoEsperado = "88888-888";

		// Execução
		resultadoAtual = adicionaMascara(TipoMascara.CEP, this.CEP);

		// Resultados
		assertNotNull(resultadoAtual);
		assertEquals(resultadoEsperado, resultadoAtual);
	}

	@Test
	public void deveRemoverMascaraCEP() {
		// Arranjos
		String resultadoEsperado = this.CEP;
		String resultadoAtual = "88888-888";

		// Execução
		resultadoAtual = removeMascaraDeNumeros(resultadoAtual);

		// Resultados
		assertNotNull(resultadoAtual);
		assertEquals(resultadoEsperado, resultadoAtual);
	}

	@Test
	public void deveLancarExcecaoQuandoDerErroAoFazerOParse() {
		// Arranjos
		String resultadoEsperado = "java.text.ParseException: Invalid character: /";

		// Execução
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			adicionaMascara(TipoMascara.CEP, "888/888");
		});

		// Resultados
		assertNotNull(exception);
		assertEquals(resultadoEsperado, exception.getMessage());
	}

	@Test
	public void deveLancarExcecaoQuandoFormatarNomeInvalido() {
		// Arranjos
		String resultadoEsperado = "java.lang.Exception: Nome Inválido";

		// Execução
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			formatarNome("Alta FLORESTA d'o'este");
		});

		// Resultados
		assertNotNull(exception);
		assertEquals(resultadoEsperado, exception.getMessage());
	}

	// @formatter:off
	@ParameterizedTest
	@CsvSource({ "JOÃO DA SILVA, João da Silva", "PEDRO das Quebrada, Pedro das Quebrada",
			"JOÃO DE OLIVEIRA, João de Oliveira", "JOÃO Do Nascimento, João do Nascimento",
			"Pedro Dos PATOS    , Pedro dos Patos", "Pedro De OLIVEIRA E Oliveira, Pedro de Oliveira e Oliveira",
			"Alta FLORESTA d'oeste, Alta Floresta D'Oeste" })
	public void deveFormatarNome(String nome, String esperado) {
		nome = formatarNome(nome);
		assertEquals(esperado, nome);
	}
	// @formatter:on
}
