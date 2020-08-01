package io.github.educontessi.domain.model;

import static io.github.educontessi.domain.helpers.util.FuncoesString.adicionaMascara;
import static io.github.educontessi.domain.helpers.util.FuncoesString.removeMascaraDeNumeros;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.educontessi.domain.helpers.util.TipoMascara;

/**
 * Entidade {@link ViaCepResposta} para retornar a responta ao endpoint de
 * consulta de CEP
 * 
 * @author Eduardo Possamai Contessi
 *
 */
public class ViaCepResposta extends BaseEntity {

	private static final long serialVersionUID = 6654071816106652912L;

	private String cep;
	private Rua rua;
	private Bairro bairro;
	private Cidade cidade;
	private Estado estado;

	public String getCep() {
		return adicionaMascara(TipoMascara.CEP, cep);
	}

	public void setCep(String cep) {
		this.cep = removeMascaraDeNumeros(cep);
	}

	public Rua getRua() {
		return rua;
	}

	public void setRua(Rua rua) {
		this.rua = rua;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@JsonIgnore
	public boolean isValid() {
		return getCep() != null && !getCep().isEmpty() && getCep().length() == 9;
	}
}
