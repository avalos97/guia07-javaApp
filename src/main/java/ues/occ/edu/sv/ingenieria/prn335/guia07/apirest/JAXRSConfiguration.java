package ues.occ.edu.sv.ingenieria.prn335.guia07.apirest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author christian
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
    
    @Override
    /**
     * Método en el cual exponemos los recursos
     * 
     */
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.resources.PeliculaREST.class);
        resources.add(ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.resources.AsientoREST.class);
        return resources;
    }
}
