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

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_usuario_duenio")
	private Persona persona;

	//bi-directional many-to-one association to CarritoProducto
	@OneToMany(mappedBy="av")
	private List<CarritoProducto> carritoProductos;

	//bi-directional many-to-one association to CategoriasEspecifica
	@OneToMany(mappedBy="av")
	private List<CategoriasEspecifica> categoriasEspecificas;

	//bi-directional many-to-many association to Persona
	@ManyToMany(mappedBy="avs2")
	private List<Persona> personas;

	//bi-directional many-to-one association to ProductosCustom
	@OneToMany(mappedBy="av")
	private List<ProductosCustom> productosCustoms;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="av")
	private List<Stock> stocks;

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

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
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

	public List<CategoriasEspecifica> getCategoriasEspecificas() {
		return this.categoriasEspecificas;
	}

	public void setCategoriasEspecificas(List<CategoriasEspecifica> categoriasEspecificas) {
		this.categoriasEspecificas = categoriasEspecificas;
	}

	public CategoriasEspecifica addCategoriasEspecifica(CategoriasEspecifica categoriasEspecifica) {
		getCategoriasEspecificas().add(categoriasEspecifica);
		categoriasEspecifica.setAv(this);

		return categoriasEspecifica;
	}

	public CategoriasEspecifica removeCategoriasEspecifica(CategoriasEspecifica categoriasEspecifica) {
		getCategoriasEspecificas().remove(categoriasEspecifica);
		categoriasEspecifica.setAv(null);

		return categoriasEspecifica;
	}

	public List<Persona> getPersonas() {
		return this.personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public List<ProductosCustom> getProductosCustoms() {
		return this.productosCustoms;
	}

	public void setProductosCustoms(List<ProductosCustom> productosCustoms) {
		this.productosCustoms = productosCustoms;
	}

	public ProductosCustom addProductosCustom(ProductosCustom productosCustom) {
		getProductosCustoms().add(productosCustom);
		productosCustom.setAv(this);

		return productosCustom;
	}

	public ProductosCustom removeProductosCustom(ProductosCustom productosCustom) {
		getProductosCustoms().remove(productosCustom);
		productosCustom.setAv(null);

		return productosCustom;
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

}