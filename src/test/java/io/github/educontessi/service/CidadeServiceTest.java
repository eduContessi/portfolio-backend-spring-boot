package io.github.educontessi.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.educontessi.model.Cidade;
import io.github.educontessi.repository.CidadeRepository;

/**
 * Classe de teste para entidade {@link CidadeService}
 * 
 * @author Eduardo Contessi
 *
 */
@SpringBootTest
public class CidadeServiceTest {

	private final Long id = 1L;

	@Mock
	private CidadeRepository repository;

	@InjectMocks
	private CidadeService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockCidade();
	}

	private void mockCidade() {
		when(repository.findById(any())).thenReturn(getCidade());
	}

	@Test
	public void deveAtualizarCidade() {
		// Arranjos
		CidadeService serviceSpy = Mockito.spy(service);
		Cidade entity = getCidade().get();

		// Execução
		serviceSpy.update(this.id, entity);

		// Resultados
		verify(serviceSpy, times(1)).update(any(), any());
		verify(repository, times(1)).findById(any());
	}

	@Test
	public void deveRetornarArrayComPropriedadesIgnoradas() {
		// Arranjos
		CidadeService serviceSpy = Mockito.spy(service);
		String[] propriedadesIgnoradas = null;
		String[] resultadoEsperado = { "id", "estado" };

		// Execução
		propriedadesIgnoradas = serviceSpy.getIgnoreProperties();

		// Resultados
		verify(serviceSpy, times(1)).getIgnoreProperties();
		assertNotNull(propriedadesIgnoradas);
		assertEquals(2, propriedadesIgnoradas.length);
		assertArrayEquals(resultadoEsperado, propriedadesIgnoradas);
	}

	private Optional<Cidade> getCidade() {
		Cidade cidade = new Cidade();
		cidade.setId(this.id);
		cidade.setNome("Teste");
		cidade.setEstadoId(this.id);
		return Optional.of(cidade);
	}

}