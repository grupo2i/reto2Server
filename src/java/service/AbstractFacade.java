package service;

import entity.Artist;
import entity.Client;
import entity.Club;
import entity.Event;
import entity.User;
import entity.Rating;
import exception.UnexpectedErrorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.ws.rs.NotAuthorizedException;

/**
 *
 * @author aitor
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractFacade.class.getName());

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Persists an entity in the database.
     *
     * @param entity The entity to be persisted.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public void create(T entity) throws UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method create on {0}", AbstractFacade.class.getName());
            getEntityManager().persist(entity);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    /**
     * Updates an entity in the database with the specified data.
     *
     * @param entity Entity with the updated data.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public void edit(T entity) throws UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method edit on {0}", AbstractFacade.class.getName());
            getEntityManager().merge(entity);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    /**
     * Removes an entity from the database.
     *
     * @param entity The entity to be removed.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public void remove(T entity) throws UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method remove on {0}", AbstractFacade.class.getName());
            getEntityManager().remove(getEntityManager().merge(entity));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    /**
     * Fiends an entity in the database using its id attribute.
     *
     * @param id The id of the entity.
     * @return The requested entity.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public T find(Object id) throws UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method find on {0}", AbstractFacade.class.getName());
            return getEntityManager().find(entityClass, id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    /**
     * Gets all the events a client has signed up for.
     *
     * @param clientId Id of the Client to get the Events.
     * @return A list of Events related to the specified Client.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public List<Event> getEventsByClientId(Integer clientId) throws UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method getEventsByClientId on {0}", AbstractFacade.class.getName());
            return getEntityManager()
                    .createNamedQuery("getEventsByClientId")
                    .setParameter("clientId", clientId)
                    .getResultList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    /**
     * Gets all the registered Clients.
     *
     * @return A list of all Clients.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public List<Client> getAllClients() throws UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method getAllClients on {0}", AbstractFacade.class.getName());
            return getEntityManager()
                    .createNamedQuery("getAllClients")
                    .getResultList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    /**
     * Gets all the registered Clubs.
     *
     * @return A list of all Clubs.
     */
    public List<Club> getAllClubs() {
        return getEntityManager()
                .createNamedQuery("getAllClubs")
                .getResultList();
    }

    /**
     * Gets all the registered Artists.
     *
     * @return A list of all Artists.
     */
    public List<Artist> getAllArtists() {
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
     *
     * @param login The login of the requested User.
     * @param password The password of the requested User.
     * @return The User with the specified data.
     * @throws NotAuthorizedException If the login and password don't match with
     * a registered user.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public User signIn(String login, String password)
            throws NotAuthorizedException, UnexpectedErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method signIn on {0}", AbstractFacade.class.getName());
            return (User) getEntityManager()
                    .createNamedQuery("signIn")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new NotAuthorizedException("User not found, incorrect login or password.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new UnexpectedErrorException(ex);
        }

    }

    public List<Rating> getAllRatingsByUserId(Integer id) throws UnexpectedErrorException {
        try {
            return getEntityManager()
                .createNamedQuery("getAllRatingsByUserId")
                .setParameter("clientId", id)
                .getResultList();
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex);
        }
    }

    public List<Rating> getAllRatingsByEventId(Integer id) throws UnexpectedErrorException {
        try {
            return getEntityManager()
                .createNamedQuery("getAllRatingsByEventId")
                .setParameter("eventId", id)
                .getResultList();
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex);
        }
    }
}
