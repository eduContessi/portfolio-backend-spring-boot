package io.github.educontessi.domain.service;

import static io.github.educontessi.domain.helpers.util.FuncoesString.removeMascaraDeNumeros;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.educontessi.domain.exception.PessoaEmUsoException;
import io.github.educontessi.domain.exception.PessoaNaoEncontradaException;
import io.github.educontessi.domain.filter.PessoaFilter;
import io.github.educontessi.domain.helpers.util.LoadProperties;
import io.github.educontessi.domain.model.Pessoa;
import io.github.educontessi.domain.repository.PessoaRepository;
import io.github.educontessi.domain.service.validator.DeletePessoaValidator;
import io.github.educontessi.domain.service.validator.ValidatorExecutor;

/**
 * Service para {@link Pessoa}
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@Service
public class PessoaService {

	private final PessoaRepository repository;

	@Autowired
	public PessoaService(PessoaRepository repository) {
		this.repository = repository;
	}

	@Cacheable(cacheNames = "PessoaService", key = "#root.method.name")
	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	public Page<Pessoa> search(PessoaFilter pessoaFilter, Pageable pageable) {
		return repository.search(pessoaFilter, pageable);
	}

	public Pessoa findById(Long id) {
		Optional<Pessoa> optionalSaved = repository.findById(id);
		if (!optionalSaved.isPresent()) {
			throw new PessoaNaoEncontradaException(id);
		}
		return optionalSaved.get();
	}

	public Pessoa findByCpfCnpj(String cpfCnpj) {
		Optional<Pessoa> optionalSaved = repository.findByCpfCnpj(removeMascaraDeNumeros(cpfCnpj));
		if (!optionalSaved.isPresent()) {
			throw new PessoaNaoEncontradaException(null, cpfCnpj);
		}
		return optionalSaved.get();
	}

	public Pessoa save(Pessoa entity) {
		Objects.requireNonNull(entity, "entity nao pode ser null");
		return repository.save(entity);
	}

	public void delete(Long id) {
		boolean excluirDefinitivo = Boolean.valueOf(LoadProperties.getProperty("portifolio.excluir-definitivo"));
		if (excluirDefinitivo) {
			definitiveDelete(id);
		} else {
			paranoidDelete(id);
		}
	}

	protected void definitiveDelete(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PessoaNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new PessoaEmUsoException(id);
		}
	}

	protected void paranoidDelete(Long id) {
		Pessoa saved = findById(id);
		validarExclusao(saved);
		saved.setDeleted(true);
		save(saved);
	}

	protected void validarExclusao(Pessoa saved) {
		ValidatorExecutor executor = new ValidatorExecutor();

		DeletePessoaValidator deleteValidator = new DeletePessoaValidator();
		deleteValidator.setPessoa(saved);
		executor.add(deleteValidator);

		executor.execute();
	}

}
