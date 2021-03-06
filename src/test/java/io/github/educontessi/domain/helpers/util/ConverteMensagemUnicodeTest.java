package io.github.educontessi.domain.helpers.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Classe de teste para entidade {@link ConverteMensagemUnicode}
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@SpringBootTest
public class ConverteMensagemUnicodeTest {

	private ConverteMensagemUnicode converteMensagemUnicode;

	@BeforeEach
	public void setUp() {
		converteMensagemUnicode = new ConverteMensagemUnicode();
	}

	// @formatter:off
	@DisplayName("Deve converter a mensagem para unicode")
	@ParameterizedTest
	@CsvSource({ "Mensagem inválida, Mensagem inv\\u00e1lida", "Recurso não encontrado, Recurso n\\u00e3o encontrado",
			"Operação não permitida, Opera\\u00e7\\u00e3o n\\u00e3o permitida" })
	public void deveConverterMensagemParaUnicode(String mensagem, String esperado) {
		mensagem = converteMensagemUnicode.converteMensagemUnicode(mensagem);
		assertEquals(esperado, mensagem);
	}
	// @formatter:on

}
