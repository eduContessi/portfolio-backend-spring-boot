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

import io.github.educontessi.model.Pessoa;
import io.github.educontessi.model.enumeracoes.Status;
import io.github.educontessi.model.enumeracoes.TipoPessoa;
import io.github.educontessi.repository.PessoaRepository;

/**
 * Classe de teste para entidade {@link PessoaService}
 * 
 * @author Eduardo Contessi
 *
 */
@SpringBootTest
public class PessoaServiceTest {

	private final Long id = 1L;

	@Mock
	private PessoaRepository repository;

	@InjectMocks
	private PessoaService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockPessoa();
	}

	private void mockPessoa() {
		when(repository.findById(any())).thenReturn(getPessoa());
	}

	@Test
	public void deveAtualizarPessoa() {
		// Arranjos
		PessoaService serviceSpy = Mockito.spy(service);
		Pessoa entity = getPessoa().get();

		// Execução
		serviceSpy.update(this.id, entity);

		// Resultados
		verify(serviceSpy, times(1)).update(any(), any());
		verify(repository, times(1)).findById(any());
	}

	@Test
	public void deveRetornarArrayComPropriedadesIgnoradas() {
		// Arranjos
		PessoaService serviceSpy = Mockito.spy(service);
		String[] propriedadesIgnoradas = null;
		String[] resultadoEsperado = { "id", "rua", "bairro", "cidade" };

		// Execução
		propriedadesIgnoradas = serviceSpy.getIgnoreProperties();

		// Resultados
		verify(serviceSpy, times(1)).getIgnoreProperties();
		assertNotNull(propriedadesIgnoradas);
		assertEquals(4, propriedadesIgnoradas.length);
		assertArrayEquals(resultadoEsperado, propriedadesIgnoradas);
	}

	private Optional<Pessoa> getPessoa() {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(this.id);
		pessoa.setNomeRazao("Teste");
		pessoa.setTipoPessoa(TipoPessoa.FISICA);
		pessoa.setStatus(Status.ATIVO);
		return Optional.of(pessoa);
	}

}