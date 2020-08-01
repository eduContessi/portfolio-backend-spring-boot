package io.github.educontessi.domain.model;

import static io.github.educontessi.domain.helpers.util.FuncoesString.formatarNome;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidade {@link Pais} para manipiular tabela de países
 * 
 * @author Eduardo Possamai Contessi
 *
 */
@Entity
@Table(name = "pais_view")
public class Pais extends BaseEntity {

	private static final long serialVersionUID = 8377594893935381151L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "sigla")
	private String sigla;

	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "bacen")
	private String bacen;

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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getBacen() {
		return bacen;
	}

	public void setBacen(String bacen) {
		this.bacen = bacen;
	}

}
