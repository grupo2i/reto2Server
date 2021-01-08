package service;

import entity.Club;
import java.util.List;
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
 * @author Martín
 */
@Stateless
@Path("entity.club")
public class ClubFacadeREST extends AbstractFacade<Club> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ClubFacadeREST() {
        super(Club.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Club entity) {
        super.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Club entity) {
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
    public Club find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    /**
     * Gets all the registered Clubs.
     * @return A list of Clubs.
     */
    @GET
    @Path("getAllClubs")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Club> getAllClubs(){
        return super.getAllClubs();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}