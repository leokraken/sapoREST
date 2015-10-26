package com.sapo.interfaces;

import java.net.URISyntaxException;

import javax.ejb.Local;

@Local
public interface IstockController {

	public void init() throws URISyntaxException;
}
