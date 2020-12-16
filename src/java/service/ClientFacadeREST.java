package service;

import entity.Client;
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
 * @author aitor
 */
@Stateless
@Path("entity.client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ClientFacadeREST() {
        super(Client.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Client entity) {
        super.create(entity);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Client entity) {
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
    public Client find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    /**
     * Gets all the events a client has signed up for.
     * @param id Id of the Client to get the Events.
     * @return A list of Events related to the specified Client.
     */
    @GET
    @Path("getEventsByClient/{id}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Event> getEventsByClientId(@PathParam("id") Integer id){
        return super.getEventsByClientId(id);
    }
    /**
     * Gets all the registered Clients.
     * @return A list of Clients.
     */
    @GET
    @Path("getAllClients")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Client> getAllClients(){
        return super.getAllClients();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}