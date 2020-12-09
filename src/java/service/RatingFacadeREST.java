package service;

import entity.Rating;
import entity.RatingId;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author aitor
 */
@Stateless
@Path("entity.rating")
public class RatingFacadeREST extends AbstractFacade<Rating> {

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

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Rating entity) {
        super.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Rating entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entity.RatingId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Rating find(@PathParam("id") PathSegment id) {
        entity.RatingId key = getPrimaryKey(id);
        return super.find(key);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}