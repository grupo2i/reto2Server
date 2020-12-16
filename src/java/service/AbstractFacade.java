package service;

import entity.Artist;
import entity.Client;
import entity.Club;
import entity.Event;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author aitor
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
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
     * @return A list of Clients.
     */
    public List<Client> getAllClients(){
        return getEntityManager()
                .createNamedQuery("getAllClients")
                .getResultList();
    }
    /**
     * Gets all the registered Clubs.
     * @return A list of Clubs.
     */
    public List<Club> getAllClubs(){
        return getEntityManager()
                .createNamedQuery("getAllClubs")
                .getResultList();
    }
    /**
     * Gets all the registered Artists.
     * @return A list of Artists.
     */
    public List<Artist> getAllArtists(){
        return getEntityManager()
                .createNamedQuery("getAllArtists")
                .getResultList();
    }
    /**
     * Looks for the User with the specified login and password.
     * @param login The login of the User signing in.
     * @param password The password of the User signing in.
     * @return The User with the specified data.
     * @throws NotFoundException If the User with the specified data does not exist.
     */
    public User signIn(String login, String password) throws NotFoundException{
        return (User) getEntityManager()
                .createNamedQuery("signIn")
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }
    
    public List<Event> getEventsByName(String name){
        return getEntityManager()
                .createNamedQuery("getEventsByName")
                .setParameter("name", name)
                .getResultList();
    }
    
    public List<Club> getClubsByName(String name){
        return getEntityManager()
                .createNamedQuery("getClubsByName")
                .setParameter("name", name)
                .getResultList();
    }
}