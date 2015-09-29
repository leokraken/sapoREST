package com.sapo.entities;

import java.io.Serializable;

import javax.jms.JMSSessionMode;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public abstract class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String apellido;

	private String nombre;

	//bi-directional many-to-one association to Av
	@JsonIgnore
	@OneToMany(mappedBy="persona")
	private List<Av> avs1;

	//bi-directional many-to-many association to Av
	@JsonIgnore
	@ManyToMany //(fetch=FetchType.EAGER)
	@JoinTable(
		name="usuarios_invitados"
		, joinColumns={
			@JoinColumn(name="id_usuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_av")
			}
		)
	private List<Av> avs2;

	//bi-directional many-to-one association to ExternalLoginAccount
	@ManyToOne
	@JoinColumn(name="account_type")
	private ExternalLoginAccount externalLoginAccount;

	public Persona() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Av> getAvs1() {
		return this.avs1;
	}

	public void setAvs1(List<Av> avs1) {
		this.avs1 = avs1;
	}

	public Av addAvs1(Av avs1) {
		getAvs1().add(avs1);
		avs1.setPersona(this);

		return avs1;
	}

	public Av removeAvs1(Av avs1) {
		getAvs1().remove(avs1);
		avs1.setPersona(null);

		return avs1;
	}

	public List<Av> getAvs2() {
		return this.avs2;
	}

	public void setAvs2(List<Av> avs2) {
		this.avs2 = avs2;
	}

	public ExternalLoginAccount getExternalLoginAccount() {
		return this.externalLoginAccount;
	}

	public void setExternalLoginAccount(ExternalLoginAccount externalLoginAccount) {
		this.externalLoginAccount = externalLoginAccount;
	}

}