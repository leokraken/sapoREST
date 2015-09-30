package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the avs database table.
 * 
 */
@Entity
@Table(name="avs")
@NamedQuery(name="Av.findAll", query="SELECT a FROM Av a")
public class Av implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descripcion;

	private String nombre;

	private String url;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario_duenio")
	private Usuario usuario;

	//bi-directional many-to-one association to CarritoProducto
	@OneToMany(mappedBy="av")
	private List<CarritoProducto> carritoProductos;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="av")
	private List<Stock> stocks;

	//bi-directional many-to-many association to Usuario
	@ManyToMany(mappedBy="avs2")
	private List<Usuario> usuarios;

	public Av() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<CarritoProducto> getCarritoProductos() {
		return this.carritoProductos;
	}

	public void setCarritoProductos(List<CarritoProducto> carritoProductos) {
		this.carritoProductos = carritoProductos;
	}

	public CarritoProducto addCarritoProducto(CarritoProducto carritoProducto) {
		getCarritoProductos().add(carritoProducto);
		carritoProducto.setAv(this);

		return carritoProducto;
	}

	public CarritoProducto removeCarritoProducto(CarritoProducto carritoProducto) {
		getCarritoProductos().remove(carritoProducto);
		carritoProducto.setAv(null);

		return carritoProducto;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setAv(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setAv(null);

		return stock;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}