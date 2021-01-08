package service;

import entity.Artist;
import entity.Client;
import entity.Club;
import entity.Event;
import entity.User;
import entity.Rating;
import exception.UnexpectedErrorException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author aitor
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Persists an entity in the database.
     * @param entity The entity to be persisted.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public void create(T entity) throws UnexpectedErrorException {
        try {
            getEntityManager().persist(entity);
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex.getMessage());
        }
        
    }

    /**
     * Updates an entity in the database with the specified data.
     * @param entity Entity with the updated data.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public void edit(T entity) throws UnexpectedErrorException {
        try {
            getEntityManager().merge(entity);
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex.getMessage());
        }
        
    }

    /**
     * Removes an entity from the database.
     * @param entity The entity to be removed.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public void remove(T entity) throws UnexpectedErrorException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex.getMessage());
        }
        
    }
    
    /**
     * Fiends an entity in the database using its id attribute.
     * @param id The id of the entity.
     * @return The requested entity.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public T find(Object id) throws UnexpectedErrorException {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex.getMessage());
        }
        
    }
    
    /**
     * Gets all the events a client has signed up for.
     * @param clientId Id of the Client to get the Events.
     * @return A list of Events related to the specified Client.
     */
    public List<Event> getEventsByClientId(Integer clientId){
        return getEntityManager()
                .createNamedQuery("getEventsByClientId")
                .setParameter("clientId", clientId)
                .getResultList();
    }
    
    /**
     * Gets all the registered Clients.
     * @return A list of all Clients.
     */
    public List<Client> getAllClients(){
        return getEntityManager()
                .createNamedQuery("getAllClients")
                .getResultList();
    }
    /**
     * Gets all the registered Clubs.
     * @return A list of all Clubs.
     */
    public List<Club> getAllClubs(){
        return getEntityManager()
                .createNamedQuery("getAllClubs")
                .getResultList();
    }
    /**
     * Gets all the registered Artists.
     * @return A list of all Artists.
     */
    public List<Artist> getAllArtists(){
        return getEntityManager()
                .createNamedQuery("getAllArtists")
                .getResultList();
    }
  
    public List<Event> getAllEvents() {
        return getEntityManager()
          .createNamedQuery("getAllEvents")
          .getResultList();
    }
    
    /**
     * Looks for the User with the specified login and password.
     * @param login The login of the requested User.
     * @param password The password of the requested User.
     * @return The User with the specified data.
     */
    public User signIn(String login, String password) {
        return (User) getEntityManager()
                .createNamedQuery("signIn")
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }    

    public List<Rating> getAllRatingsByUserId(Integer id) {
        return getEntityManager()
          .createNamedQuery("getAllRatingsByUserId")
          .setParameter("clientId", id)
          .getResultList();
    }

    public List<Rating> getAllRatingsByEventId(Integer id) {
        return getEntityManager()
          .createNamedQuery("getAllRatingsByEventId")
          .setParameter("eventId", id)
          .getResultList();
    }
}
