package service;

import entity.Event;
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
 * @author Matteo
 */
@Stateless
@Path("entity.event")
public class EventFacadeREST extends AbstractFacade<Event> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public EventFacadeREST() {
        super(Event.class);
    }

    /**
     * Creates event entity
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Event entity) {
        super.create(entity);
    }

    /**
     * Is for edit the event entity
     *
     * @param entity
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Event entity) {
        super.edit(entity);
    }

    /**
     * Removes the event by id
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    /**
     * Finds the event by id
     *
     * @param id
     * @return the events
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Event find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * Gets all events
     *
     * @return a list of events
     */
    @GET
    @Path("getAllEvents")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Event> getAllEvents() {
        return super.getAllEvents();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
