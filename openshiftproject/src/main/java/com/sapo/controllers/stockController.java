package com.sapo.controllers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Stateless
@LocalBean
@Path("/stock")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class stockController {

    public stockController() {
    }

}
