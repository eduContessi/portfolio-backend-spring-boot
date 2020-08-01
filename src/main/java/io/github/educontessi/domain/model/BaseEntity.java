package io.github.educontessi.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.github.educontessi.domain.converters.LocalDateTimeToDateConverter;

@MappedSuperclass
@JsonInclude(Include.NON_NULL)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = -5030708008265402419L;

	@Column(name = "create_date", insertable = false, updatable = false)
	@Convert(converter = LocalDateTimeToDateConverter.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	protected LocalDateTime created;

	@Column(name = "changed", insertable = false, updatable = false)
	@Convert(converter = LocalDateTimeToDateConverter.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	protected LocalDateTime changed;

	@Column(name = "deleted", columnDefinition = "tinyint(1) default 1", insertable = false, updatable = true)
	protected boolean deleted;

	@Column(name = "delete_date", insertable = false, updatable = true)
	@Convert(converter = LocalDateTimeToDateConverter.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	protected LocalDateTime deletedDate;

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getChanged() {
		return changed;
	}

	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
		this.setDeletedDate(this.deleted ? LocalDateTime.now() : null);
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}

}
