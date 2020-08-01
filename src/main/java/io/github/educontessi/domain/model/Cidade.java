package io.github.educontessi.domain.model;

import static io.github.educontessi.domain.helpers.util.FuncoesString.formatarNome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidade {@link Cidade} para manipiular tabela de cidades
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@Entity
@Table(name = "cidade_view")
public class Cidade extends BaseEntity {

	private static final long serialVersionUID = -6368847171271292284L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Column(name = "estado_id", insertable = true, updatable = true)
	private Long estadoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estado_id", insertable = false, updatable = false)
	private Estado estado;

	@NotNull
	@Column(name = "ibge")
	private Integer ibge;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = formatarNome(nome);
	}

	public Long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Long estadoId) {
		this.estadoId = estadoId;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
		this.estadoId = estado.getId();
	}

	public Integer getIbge() {
		return ibge;
	}

	public void setIbge(Integer ibge) {
		this.ibge = ibge;
	}

}
