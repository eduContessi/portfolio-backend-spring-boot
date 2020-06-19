package io.github.educontessi.api.dataconverter;

import io.github.educontessi.api.dto.BaseDto;
import io.github.educontessi.domain.model.BaseEntity;

public abstract class DataConverter<E extends BaseEntity, D extends BaseDto> {

	/**
	 * Copiar os dados do DTO para a entidade
	 * 
	 * @param entity instance da entidade que vai receber os dados
	 * @param dto    objeto que contém os dados
	 */
	public abstract void copyToEntity(E entity, D dto);

	/**
	 * Copiar os dados da entidade para o DTO
	 * 
	 * @param dto    new DTO
	 * @param entity entidade que contém os dados
	 * @return dto
	 */
	public abstract D convertToDto(D dto, E entity, String expandir);

}