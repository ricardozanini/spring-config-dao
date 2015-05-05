package br.com.tecnobiz.spring.config.dao.examples.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
public class SuperHeroi implements Serializable, Comparable<SuperHeroi> {

	private static final long serialVersionUID = 6431182347300795085L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 255)
	private String nome;

	@Column(length = 32)
	private String superPoder;

	public SuperHeroi() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSuperPoder() {
		return superPoder;
	}

	public void setSuperPoder(String superPoder) {
		this.superPoder = superPoder;
	}

	@Override
	public int compareTo(SuperHeroi o) {
		return this.nome.compareToIgnoreCase(o.getNome());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.nome);
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(id).addValue(this.nome)
				.addValue(this.superPoder).toString();

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != getClass()) {
			return false;
		}

		SuperHeroi that = (SuperHeroi) obj;

		return equal(this.id, that.getId()) && equal(this.nome, that.getNome());
	}
}
