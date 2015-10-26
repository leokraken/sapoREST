package com.sapo.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="tipo_cuenta")
@NamedQuery(name="TipoCuenta.findAll", query="SELECT t FROM TipoCuenta t")
public class TipoCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String descripcion;

	private String nombre;
	
	private double precio;

	@OneToMany(mappedBy="tipocuenta", cascade=CascadeType.REMOVE)
	List<Usuario> usuarios;
	
	public TipoCuenta() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public void addUsuario(Usuario u){
		this.usuarios.add(u);
		u.setTipocuenta(this);
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	
}