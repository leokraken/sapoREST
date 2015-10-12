package com.sapo.security;

import javax.persistence.EntityManager;

import com.sapo.entities.Administrador;
import com.sapo.entities.Av;
import com.sapo.entities.TokensAdministrador;
import com.sapo.entities.TokensUsuario;

public class SecurityUtils {
	
	public Boolean authorizeAdmin(String usertoken, String user, EntityManager em){
		if(usertoken == null || user == null) return false;
		try{

			Administrador a = em.find(Administrador.class, user);
			for(TokensAdministrador ta : a.getTokensAdministradors()){
				if(ta.getId().getToken().equals(usertoken))
					return true;
			}		
		}catch(Exception e){
			
		}
		return false;
	}

	public Boolean authorizeUser(String usertoken, String user,Long AlmacenID, EntityManager em){		
		try{
			Av av = em.find(Av.class, AlmacenID);
			if(av.getUsuario().getId().equals(user))
			for(TokensUsuario ta : av.getUsuario().getTokensUsuarios()){
				if(ta.getId().getToken().equals(usertoken)) 
					return true;
			}			
		}catch(Exception e){
			
		}
		return false;
	}
	
}
