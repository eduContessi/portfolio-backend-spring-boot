package io.github.educontessi.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.educontessi.dataconverter.PaisV1DataConverter;
import io.github.educontessi.dto.PaisV1Dto;
import io.github.educontessi.model.Pais;
import io.github.educontessi.service.PaisService;

/**
 * Endpoints para {@link Pais}
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@RestController
@RequestMapping("/v1/paises")
public class PaisV1Resource extends BaseResource {

	@Autowired
	private PaisService service;

	@Autowired(required = true)
	private PaisV1DataConverter converter;

	@GetMapping
	public List<PaisV1Dto> findAll() {
		List<Pais> lista = service.findAll();
		List<PaisV1Dto> listaDto = new ArrayList<>();
		listaDto.addAll(lista.stream().map(x -> converter.convertToDto(x)).collect(Collectors.toList()));

		return listaDto;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaisV1Dto> findById(@PathVariable Long id) {
		Optional<Pais> categoria = service.findById(id);
		return categoria.isPresent() ? ResponseEntity.ok(converter.convertToDto(categoria.get()))
				: ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody PaisV1Dto dto, HttpServletResponse response) {
		Pais entity = new Pais();
		converter.copyToEntity(entity, dto);
		entity = service.save(entity);

		converter.convertToDto(dto, entity);
		return created(entity.getId(), response, dto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody Pais entity) {
		try {
			Pais pais = service.update(id, entity);
			return ok(pais);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}

}
