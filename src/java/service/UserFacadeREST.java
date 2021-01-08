package service;

import entity.User;
import exception.UnexpectedErrorException;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import security.Hashing;
import security.PublicDecrypt;

/**
 *
 * @author Aitor Fidalgo
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    /**
     * Persists a User in the database.
     * @param entity The User to be persisted.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) throws InternalServerErrorException {
        try {
            super.create(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        
    }

    /**
     * Updates a User in the database with the specified data.
     * @param entity User with the updated data.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(User entity) throws InternalServerErrorException {
        try {
            super.edit(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        
    }

    /**
     * Removes a User from the database.
     * @param id Id of the User to be removed.
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
     * Fiends a User in the database using its id attribute.
     * @param id The id of the User.
     * @return The requested User without the password attribute.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Integer id) throws InternalServerErrorException {
        User user = null;
        try{
            user = super.find(id);
            if(user == null) throw new InternalServerErrorException();
            //Detaching user so that changes are not updated in the DB.
            em.detach(user);
            //Deleting users password to send it trough the network.
            user.setPassword("");
        } catch(UnexpectedErrorException ex){
            throw new InternalServerErrorException(ex);
        }
        
        return user;
    }
    
    /**
     * Looks for the User with the specified login and password.
     * @param login The specified login.
     * @param encodedPasswordStr The encoded specified password.
     * @return The User with the specified data.
     */
    @GET
    @Path("signIn/{login}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public User signIn(@PathParam("login") String login,
            @PathParam("password") String encodedPasswordStr)
            throws NotFoundException, InternalServerErrorException {
        User user = null;
        try{
            //Convert the String value of the encoded password to byte array.
            byte[] encodedPassword = Base64.getDecoder().decode(encodedPasswordStr);
            //Decrypting and hashing the password sent from the client.
            String password = Hashing.cifrarTexto(new String(PublicDecrypt
                    .decode(encodedPassword)));
            user = super.signIn(login, password);
            if(user == null) throw new NotFoundException("User not found, incorrect login or password.");
            
            //Detaching user so that changes are not updated in the DB.
            em.detach(user);
            //Deleting users password to send it trough the network.
            user.setPassword("");
        } catch (NotFoundException | NoResultException ex){
            throw new NotFoundException(ex);
        } catch (Exception ex) {
            throw new InternalServerErrorException(new UnexpectedErrorException(ex.getMessage()));
        }
        
        return user;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
