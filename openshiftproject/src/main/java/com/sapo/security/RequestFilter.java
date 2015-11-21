package com.sapo.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RequestFilter implements ContainerRequestFilter {
    
    @Override
    public void filter( ContainerRequestContext requestCtx ) throws IOException {
        System.out.println( "Executing REST request filter" );
 
        // When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
       /*
        if ( requestCtx.getRequest().getMethod().equals( "OPTIONS" ) ) {
            // Just send a OK signal back to the browser
            requestCtx.abortWith( Response.status( Response.Status.OK ).build() );
        }*/
    }
}
