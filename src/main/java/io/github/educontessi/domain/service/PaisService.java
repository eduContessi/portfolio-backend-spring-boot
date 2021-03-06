package io.github.educontessi.domain.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.educontessi.domain.exception.PaisEmUsoException;
import io.github.educontessi.domain.exception.PaisNaoEncontradoException;
import io.github.educontessi.domain.filter.PaisFilter;
import io.github.educontessi.domain.helpers.util.LoadProperties;
import io.github.educontessi.domain.model.Pais;
import io.github.educontessi.domain.repository.PaisRepository;
import io.github.educontessi.domain.service.validator.DeletePaisValidator;
import io.github.educontessi.domain.service.validator.ValidatorExecutor;

/**
 * Service para {@link Pais}
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@Service
public class PaisService {

	@Autowired
	private PaisRepository repository;

	public List<Pais> findAll() {
		return repository.findAll();
	}

	public Page<Pais> search(PaisFilter filter, Pageable pageable) {
		return repository.search(filter, pageable);
	}

	public Pais findById(Long id) {
		Optional<Pais> optionalSaved = repository.findById(id);
		if (!optionalSaved.isPresent()) {
			throw new PaisNaoEncontradoException(id);
		}
		return optionalSaved.get();
	}

	public Pais save(Pais entity) {
		Objects.requireNonNull(entity, "entity nao pode ser null");
		return repository.saveAndFlush(entity);
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
			throw new PaisNaoEncontradoException(id);

		} catch (DataIntegrityViolationException e) {
			throw new PaisEmUsoException(id);
		}
	}

	protected void paranoidDelete(Long id) {
		Pais saved = findById(id);
		validarExclusao(saved);
		saved.setDeleted(true);
		save(saved);
	}

	protected void validarExclusao(Pais saved) {
		ValidatorExecutor executor = new ValidatorExecutor();

		DeletePaisValidator deleteValidator = new DeletePaisValidator();
		deleteValidator.setPais(saved);
		executor.add(deleteValidator);

		executor.execute();
	}

}
