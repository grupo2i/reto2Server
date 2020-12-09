package service;

import entity.Artist;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author aitor
 */
@Stateless
@Path("entity.artist")
public class ArtistFacadeREST extends AbstractFacade<Artist> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ArtistFacadeREST() {
        super(Artist.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Artist entity) {
        super.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Artist entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Artist find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}