package service;

import entity.Event;
import exception.UnexpectedErrorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @throws exception.UnexpectedErrorException
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Event entity) throws UnexpectedErrorException {
        super.create(entity);
    }

    /**
     * Is for edit the event entity
     *
     * @param entity
     * @throws exception.UnexpectedErrorException
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Event entity) throws UnexpectedErrorException {
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
        try {
            super.remove(super.find(id));
        } catch (UnexpectedErrorException ex) {
            Logger.getLogger(EventFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Finds the event by id
     *
     * @param id the event id
     * @return the events
     * @throws exception.UnexpectedErrorException
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Event find(@PathParam("id") Integer id) throws UnexpectedErrorException {
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
