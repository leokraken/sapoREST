package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the external_login_account database table.
 * 
 */
@Entity
@Table(name="external_login_account")
@NamedQuery(name="ExternalLoginAccount.findAll", query="SELECT e FROM ExternalLoginAccount e")
public class ExternalLoginAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String proveedor;

	//bi-directional many-to-one association to Persona
	@JsonIgnore
	@OneToMany(mappedBy="externalLoginAccount")
	private List<Persona> personas;

	public ExternalLoginAccount() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public List<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public Persona addPersona(Persona persona) {
		getPersonas().add(persona);
		persona.setExternalLoginAccount(this);

		return persona;
	}

	public Persona removePersona(Persona persona) {
		getPersonas().remove(persona);
		persona.setExternalLoginAccount(null);

		return persona;
	}

}