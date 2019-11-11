/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ues.occ.edu.sv.ingenieria.prn335.cineData.entity.Pelicula;
import ues.occ.edu.sv.ingenieria.prn335.guia07.apirest.PeliculaFacade;

/**
 *
 * @author christian
 */
@Path("Pelicula")
@RequestScoped
public class PeliculaREST implements Serializable{
    
    @Inject
    PeliculaFacade peliculaFacade;
    
    /**
     * Metodo que devuleve el numero de registros en la entity, Con la notacion
     * GET Consumido de tipo Json
     *
     * @return retornara el numero de registros en la entity, por defecto se
     * retornara 0
     */
    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public Response Count() {
        if (peliculaFacade != null) {
            try {
                int totalRegistros = peliculaFacade.count();
                if (totalRegistros != 0) {
                    return Response.status(Response.Status.OK)
                            .entity(totalRegistros).build();
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }
        return Response.status(Response.Status.NOT_FOUND)
                .header("No se encontraron Registros", peliculaFacade)
                .build();
    }

    /**
     *
     * @param identificador
     * @return un response, devolvera el registro que sea de igual id con el
     * parametro ingresado
     */
    @GET
    @Path("id")
    @Produces({MediaType.APPLICATION_JSON})
    public Response buscarid(@QueryParam("id") Integer identificador) {

        Pelicula buscado = peliculaFacade.find(identificador);

        try {
            if (identificador >= 0 && buscado != null) {
                return Response.status(Response.Status.OK).entity(buscado).build();
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        return Response.status(Response.Status.BAD_REQUEST).header("no se encontro registro:", identificador).build();
    }

    /**
     *
     * @return la lista con todos los registros, por defecto se retornara la
     * lista vacia
     */
    @GET
    @Path("todo")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Pelicula> buscarTodo() {//Busqueda realizada en toda la bd, de cada registro
        List<Pelicula> lista = new ArrayList<>();

        if (peliculaFacade != null) {
            try {
                if (peliculaFacade.findAll() != null) {
                    return lista = peliculaFacade.findAll();
                    
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }
        return lista = Collections.EMPTY_LIST;
    }

    /**
     *
     * @param primero
     * @param pagezise
     * @return un response devolvera la lista que esten desde el inicio
     * ingresado y mostrara la cantidad de datos que se le proporcione, por
     * defecto se retornara la lista vacia
     */
    @GET
    @Path("Range")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Pelicula> buscarPorRango( //busqueda realizada entre un rango, pasando por defecto desde cero hasta 10
            @QueryParam("inicio") @DefaultValue("0") int primero,
            @QueryParam("cantidad") @DefaultValue("10") int pagezise) {

        List<Pelicula> lista;
        if (peliculaFacade != null) {//verificamos si el facade no es nulo
            try {

                return lista = peliculaFacade.findRange(primero, pagezise); //retornamos la lista con los registros en ese dato

            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        return lista = Collections.EMPTY_LIST; // si no se encuentran los registros, retornamos una lista vacia
    }


    /**
     * Crea un registro de la entidad, Injectado del metodo POST Consumido en
     * formato Json, recibira un registrro completo de la entiry para crear
     *
     * @param registro
     * @return
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Pelicula Crear(Pelicula registro) {

        if (registro != null) {
            try {
                if (peliculaFacade != null) {
                    Pelicula nuevo = peliculaFacade.crear(registro);
                    if (nuevo != null) {
                        return nuevo;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return new Pelicula();
    }
        
    

    /**
     * Edicion un registro de la entidad, Injectado del metodo PUT Consumido en
     * formato Json recibira un registro completo de la entity a editar
     * 
     * 
     *
     * @param registro
     * @return
     */
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Pelicula Modificar(Pelicula registro) {
        if (registro != null) {
            try {
                if (peliculaFacade != null) {
                    Pelicula nuevo = peliculaFacade.editar(registro);
                    if (nuevo != null) {
                        return nuevo;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return new Pelicula();
    }

    /**
     * Eliminacion un registro de la entidad, Injectado del metodo PUT Consumido
     * en formato Json, sera buscado por el id del registro
     *
     * @param identificador
     * @return un Response
     */
    @DELETE
    @Path("id")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Pelicula eliminar(@QueryParam("id") int idTipo) {
        if (idTipo > 0) {
            try {
                if (peliculaFacade != null) {
                    Pelicula die = peliculaFacade.remover(peliculaFacade.find(idTipo));
                    if (die != null) {
                        return die;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return new Pelicula();
    }
}
