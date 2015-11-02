package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the productos database table.
 * 
 */
@Entity
@Table(name="productos")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String descripcion;

	private Boolean generico;

	private String nombre;

	//bi-directional many-to-one association to Atributo
	@OneToMany(mappedBy="producto",cascade=CascadeType.REMOVE)
	private List<Atributo> atributos;

	//bi-directional many-to-one association to CarritoProducto
	@OneToMany(mappedBy="producto", cascade=CascadeType.REMOVE)
	private List<CarritoProducto> carritoProductos;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="categoriaid")
	private Categoria categoria;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="producto",cascade=CascadeType.REMOVE)
	private List<Stock> stocks;

	public Producto() {
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

	public Boolean getGenerico() {
		return this.generico;
	}

	public void setGenerico(Boolean generico) {
		this.generico = generico;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Atributo> getAtributos() {
		return this.atributos;
	}

	public void setAtributos(List<Atributo> atributos) {
		this.atributos = atributos;
	}

	public Atributo addAtributo(Atributo atributo) {
		getAtributos().add(atributo);
		atributo.setProducto(this);

		return atributo;
	}

	public Atributo removeAtributo(Atributo atributo) {
		getAtributos().remove(atributo);
		atributo.setProducto(null);

		return atributo;
	}

	public List<CarritoProducto> getCarritoProductos() {
		return this.carritoProductos;
	}

	public void setCarritoProductos(List<CarritoProducto> carritoProductos) {
		this.carritoProductos = carritoProductos;
	}

	public CarritoProducto addCarritoProducto(CarritoProducto carritoProducto) {
		getCarritoProductos().add(carritoProducto);
		carritoProducto.setProducto(this);

		return carritoProducto;
	}

	public CarritoProducto removeCarritoProducto(CarritoProducto carritoProducto) {
		getCarritoProductos().remove(carritoProducto);
		carritoProducto.setProducto(null);

		return carritoProducto;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setProducto(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setProducto(null);

		return stock;
	}

	
}