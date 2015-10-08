package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
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

	//bi-directional many-to-one association to Administrador
	@OneToMany(mappedBy="externalLoginAccount")
	private List<Administrador> administradores;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="externalLoginAccount")
	private List<Usuario> usuarios;

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

	public List<Administrador> getAdministradores() {
		return this.administradores;
	}

	public void setAdministradores(List<Administrador> administradores) {
		this.administradores = administradores;
	}

	public Administrador addAdministradore(Administrador administradore) {
		getAdministradores().add(administradore);
		administradore.setExternalLoginAccount(this);

		return administradore;
	}

	public Administrador removeAdministradore(Administrador administradore) {
		getAdministradores().remove(administradore);
		administradore.setExternalLoginAccount(null);

		return administradore;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setExternalLoginAccount(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setExternalLoginAccount(null);

		return usuario;
	}

}