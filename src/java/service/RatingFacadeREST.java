package service;

import entity.Client;
import entity.Event;
import entity.Rating;
import entity.RatingId;
import exception.UnexpectedErrorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author ander
 */
@Stateless
@Path("entity.rating")
public class RatingFacadeREST extends AbstractFacade<Rating> {

    @EJB
    private ClientFacadeREST clientFacadeREST;

    @EJB
    private EventFacadeREST eventFacadeREST;

    private static final Logger LOGGER = Logger.getLogger(RatingFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    private RatingId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;clientId=clientIdValue;eventId=eventIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.RatingId key = new entity.RatingId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> clientId = map.get("clientId");
        if (clientId != null && !clientId.isEmpty()) {
            key.setClientId(new java.lang.Integer(clientId.get(0)));
        }
        java.util.List<String> eventId = map.get("eventId");
        if (eventId != null && !eventId.isEmpty()) {
            key.setEventId(new java.lang.Integer(eventId.get(0)));
        }
        return key;
    }

    public RatingFacadeREST() {
        super(Rating.class);
    }

    /**
     * Persists a Rating in the database.
     *
     * @param entity
     * @throws InternalServerErrorException if anything goes wrong.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Rating entity) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Editing create {0}", entity);
            Client client = clientFacadeREST.find(entity.getId().getClientId());
            Event event = eventFacadeREST.find(entity.getId().getEventId());
            entity.setClient(client);
            entity.setEvent(event);
            super.create(entity);
        } catch (UnexpectedErrorException e) {
            LOGGER.log(Level.INFO, "Could not create entity {0}", entity);
            throw new InternalServerErrorException(e.getMessage());
        } catch (NoContentException ex) {
            Logger.getLogger(RatingFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates a Rating in the database with the specified data.
     *
     * @param entity
     * @throws InternalServerErrorException if anything goes wrong.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Rating entity) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Editing entity {0}", entity);
            super.edit(entity);
        } catch (UnexpectedErrorException e) {
            LOGGER.log(Level.INFO, "Could not edit entity {0}", entity);
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Removes a Rating from the database.
     *
     * @param id
     * @throws InternalServerErrorException if anything goes wrong.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) throws InternalServerErrorException {
        entity.RatingId key = getPrimaryKey(id);
        try {
            LOGGER.log(Level.INFO, "Removing {0}", id);
            super.remove(super.find(key));
        } catch (UnexpectedErrorException e) {
            LOGGER.log(Level.INFO, "Could not remove {0}", id);
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    /**
     * Finds a rating using its id attribute
     *
     * @param id
     * @return
     * @throws InternalServerErrorException in case anything goes wrong
     * @throws NoResultException if there are no ratings
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Rating find(@PathParam("id") PathSegment id) throws InternalServerErrorException, NoResultException {
        entity.RatingId key = getPrimaryKey(id);
        Rating rating = new Rating();

        try {
            rating = super.find(key);
            if (rating == null) {
                LOGGER.log(Level.INFO, "No ratings found for event {0}", id);
                throw new NoResultException();
            }
        } catch (UnexpectedErrorException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex.getMessage());
        }
        LOGGER.log(Level.INFO, "Reading ratings for event {0}", id);

        return rating;

    }

    /**
     * Finds a rating by a user Id.
     *
     * @param id
     * @return
     * @throws InternalServerErrorException if anything goes wrong
     */
    @GET
    @Path("getAllRatingsByUserId/{id}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Rating> getAllRatingsByUserId(@PathParam("id") Integer id) throws InternalServerErrorException {
        List rating;

        try {
            rating = super.getAllRatingsByUserId(id);
            if (rating.isEmpty()) {
                LOGGER.log(Level.INFO, "No ratings found for event {0}", id);
                throw new NoResultException();
            }
        } catch (UnexpectedErrorException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        LOGGER.log(Level.INFO, "Reading ratings for event {0}", id);
        return rating;
    }

    /**
     * Finds a rating by a event Id.
     *
     * @param id
     * @return
     * @throws InternalServerErrorException if anything goes wrong
     */
    @GET
    @Path("getAllRatingsByEventId/{id}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Rating> getAllRatingsByEventId(@PathParam("id") Integer id) throws InternalServerErrorException {
        List rating;

        try {
            rating = super.getAllRatingsByEventId(id);
            if (rating.isEmpty()) {
                LOGGER.log(Level.INFO, "No ratings found for event {0}", id);
                throw new NoResultException();
            }
        } catch (UnexpectedErrorException ex) {
            LOGGER.severe(ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        LOGGER.log(Level.INFO, "Reading ratings for event {0}", id);
        return rating;
    }

    /**
     * @return EntityManager instance used in the class.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
