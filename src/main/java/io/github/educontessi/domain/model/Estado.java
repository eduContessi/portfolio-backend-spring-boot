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
 * Entidade {@link Estado} para manipiular tabela de estados
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@Entity
@Table(name = "estado_view")
public class Estado extends BaseEntity {

	private static final long serialVersionUID = -1558660809629630787L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "uf")
	private String uf;

	@NotNull
	@Column(name = "pais_id", insertable = true, updatable = true)
	private Long paisId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pais_id", insertable = false, updatable = false)
	private Pais pais;

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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Long getPaisId() {
		return paisId;
	}

	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
		this.paisId = pais.getId();
	}

}
