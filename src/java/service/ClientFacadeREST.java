package service;

import entity.Client;
import entity.Event;
import exception.UnexpectedErrorException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Aitor Fidalgo
 */
@Stateless
@Path("entity.client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ClientFacadeREST() {
        super(Client.class);
    }

    /**
     * Persists a Client in the database.
     * @param entity The Client to be persisted.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Client entity) throws InternalServerErrorException{
        try {
            super.create(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Updates a Client in the database with the specified data.
     * @param entity Client with the updated data.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Client entity) throws InternalServerErrorException {
        try {
            super.edit(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Removes a Client from the database.
     * @param id Id of the Client to be removed.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws InternalServerErrorException {
        try {
            super.remove(super.find(id));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Fiends a Client in the database using its id attribute.
     * @param id The id of the client.
     * @return The requested client.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Client find(@PathParam("id") Integer id) throws InternalServerErrorException{
        try {
            return super.find(id);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        
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
    public List<Event> getEventsByClientId(@PathParam("id") Integer id) 
            throws NotFoundException, InternalServerErrorException{
        List<Event> events;
        try {
            events = super.getEventsByClientId(id);
            //Throwing exception if no events are found.
            if (events.isEmpty())
                throw new NotFoundException("No events found for the client.");
            
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
        
        return events;
    }
    
    /**
     * Gets all the registered Clients.
     * @return A list of Clients.
     */
    @GET
    @Path("getAllClients")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Client> getAllClients() throws NotFoundException, InternalServerErrorException {
        List<Client> clients;
        try {
            clients = super.getAllClients();
            if (clients.isEmpty()) 
                throw new NotFoundException("There no  clients registered in the database.");
            
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
        
        return clients;
    }

    /**
     * @return EntityManager instance used in the class.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}