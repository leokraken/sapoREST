package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the administradores database table.
 * 
 */
@Entity
@Table(name="administradores")
@NamedQuery(name="Administrador.findAll", query="SELECT a FROM Administrador a")
public class Administrador extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	public Administrador() {
	}
	
}