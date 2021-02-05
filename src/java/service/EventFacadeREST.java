package service;

import entity.Artist;
import entity.Event;
import entity.Rating;
import exception.UnexpectedErrorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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

    private static final Logger LOGGER = Logger.getLogger(AbstractFacade.class.getName());
    
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
        LOGGER.log(Level.INFO, "Starting method find on {0}", EventFacadeREST.class.getName());
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
        LOGGER.log(Level.INFO, "Starting method find on {0}", EventFacadeREST.class.getName());
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

    /**
     * Changes Event place if Ariana Grande
     * @param place
     * @throws exception.UnexpectedErrorException
     */
    @PUT
    @Path("updateArianaGrande/{place}")
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void updateArianaGrande(String place) throws UnexpectedErrorException {
        LOGGER.log(Level.INFO, "Place {0}", place);
        List<Event> events = getAllEvents();
        for(Event e : events) {
            if(e.getName().contains("Ariana Grande")) {
                e.setPlace(place);
                try {
                    edit(e);
                } catch (UnexpectedErrorException ex) {
                    throw ex;
                }
            }
        }
    }

    /**
     * Finds a rating by a user Id.
     *
     * @param id
     * @return
     * @throws InternalServerErrorException if anything goes wrong
     */
    @GET
    @Path("getAllEventsByClubId/{id}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Event> getAllEventsByClubId(@PathParam("id") Integer id) throws InternalServerErrorException {
        List event;

        try {
            event = super.getAllEventsByClubId(id);
            if (event.isEmpty()) {
                LOGGER.log(Level.INFO, "No events found for club {0}", id);
                throw new NoResultException();
            }
        } catch (UnexpectedErrorException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        LOGGER.log(Level.INFO, "Reading events for club {0}", id);
        return event;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
