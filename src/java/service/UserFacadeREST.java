package service;

import entity.User;
import exception.UnexpectedErrorException;
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
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import security.Hashing;
import security.PublicCrypt;
import security.PublicDecrypt;

/**
 * Defines REST services for User entity.
 *
 * @see User
 * @author Aitor Fidalgo
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    private static final Logger LOGGER = Logger.getLogger(UserFacadeREST.class.getName());

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    /**
     * Persists a User in the database.
     *
     * @param entity The User to be persisted.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method create on {0}", UserFacadeREST.class.getName());
            //Decoding password with RSA and encoding with SHA.
            entity.setPassword(Hashing.encode(PublicDecrypt.decode(entity.getPassword())));
            super.create(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }

    }

    /**
     * Updates a User in the database with the specified data.
     *
     * @param entity User with the updated data.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(User entity) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method edit on {0}", UserFacadeREST.class.getName());
            //Decoding password with RSA.
            //The password is already encoded with SHA.
            entity.setPassword(new String(PublicDecrypt.decode(entity.getPassword())));
            super.edit(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }

    }

    /**
     * Removes a User from the database.
     *
     * @param id Id of the User to be removed.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method remove on {0}", UserFacadeREST.class.getName());
            super.remove(super.find(id));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }

    }

    /**
     * Fiends a User in the database using its id attribute.
     *
     * @param id The id of the User.
     * @return The requested User without the password attribute.
     * @throws InternalServerErrorException If anything goes wrong.
     * @throws javax.ws.rs.core.NoContentException If the retrieved User is null.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Integer id) throws InternalServerErrorException, NoContentException {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "Starting method find on {0}", UserFacadeREST.class.getName());
            user = super.find(id);
            if (user == null) {
                throw new NoContentException("The specified user does not exist.");
            }
            //Detaching user to encode the password with RSA.
            //The password is already encoded with SHA.
            em.detach(user);
            //Encoding password with RSA.
            user.setPassword(PublicCrypt.encode(user.getPassword()));
        } catch (UnexpectedErrorException | NoContentException ex) {
            throw new InternalServerErrorException(ex);
        }

        return user;
    }

    /**
     * Looks for the User with the specified login and password.
     *
     * @param login The specified login.
     * @param encodedPassword The encoded specified password.
     * @return The User with the specified data.
     */
    @GET
    @Path("signIn/{login}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public User signIn(@PathParam("login") String login,
            @PathParam("password") String encodedPassword)
            throws InternalServerErrorException, NotAuthorizedException {
        User user = null;
        try {
            LOGGER.log(Level.INFO, "Starting method signIn on {0}", UserFacadeREST.class.getName());
            //Decoding the password with RSA and then encoding it with SHA.
            String password = Hashing.encode(PublicDecrypt
                    .decode(encodedPassword));
            user = super.signIn(login, password);
            if (user == null) {
                throw new NotAuthorizedException("User not found, incorrect login or password.");
            }
            
            //Detaching user to encode the password with RSA.
            //The password is already encoded with SHA.
            em.detach(user);
            //Encoding password with RSA.
            user.setPassword(PublicCrypt.encode(user.getPassword()));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }

        return user;
    }

    /**
     * Looks for the UserPrivilege with the specified user.
     *
     * @param login The specified login.
     * @return The User with the specified data.
     */
    @GET
    @Path("getPrivilege/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User getPrivilege(@PathParam("login") String login) throws InternalServerErrorException, NotAuthorizedException {
        try {
            LOGGER.log(Level.INFO, "Starting method getPrivilege on {0}", UserFacadeREST.class.getName());
            //Encapsulating userPrivilege attribute on a User object
            //to avoid xml syntax error due to plain text production.
            User user = new User();
            user.setUserPrivilege(super.getUserByLogin(login).getUserPrivilege());
            return user;
        } catch(NotAuthorizedException ex) {
            throw new NotAuthorizedException("User not found, incorrect login or password.", ex.getResponse());
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }
    
    @GET
    @Path("getUserByLogin/{login}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public User getUserByLogin(@PathParam("login") String login) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method getUserByLoginv on {0}", UserFacadeREST.class.getName());
            User user = super.getUserByLogin(login);
            //Detaching user to encode the password with RSA.
            //The password is already encoded with SHA.
            em.detach(user);
            //Encoding password with RSA.
            user.setPassword(PublicCrypt.encode(user.getPassword()));
            return user;
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * Return the User with the specified email from the database.
     * 
     * @param email The specified email.
     * @return User with the specified email.
     * @throws InternalServerErrorException If anything goes wrong.
     * @throws NotAuthorizedException If no User with specified email if found.
     */
    @GET
    @Path("getUserByEmail/{email}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public User getUserByEmail(@PathParam("email") String email) throws InternalServerErrorException, NotAuthorizedException {
        LOGGER.log(Level.INFO, "Starting method getUserByEmail on {0}", UserFacadeREST.class.getName());
        User user;
        try {
            user = super.getUserByEmail(email);
            if(user == null) {
                throw new NotAuthorizedException("User not found, incorrect email.");
            }
            em.detach(user);
            //Encoding password with RSA.
            user.setPassword(PublicCrypt.encode(user.getPassword()));
        } catch(NoResultException ex) {
            throw new NotAuthorizedException("User not found, incorrect email.");
        } catch(UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        return user;
    }
    
    /**
     * @return EntityManager instance used in the class.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
