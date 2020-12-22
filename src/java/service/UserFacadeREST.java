package service;

import entity.User;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
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
 * @author aitor
 */
@Stateless
@Path("entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, User entity) {
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
    public User find(@PathParam("id") Integer id) {
        User user = null;
        user = super.find(id);
        //Detaching user so that changes are not updated in the DB...
        em.detach(user.getRatings());
        em.detach(user);
        user.setPassword("");
        
        return user;
    }
    /**
     * Looks for the User with the specified login and password.
     * @param login The login of the User signing in.
     * @param encodedPasswordStr The encoded password of the User signing in.
     * @return The User with the specified data.
     */
    @GET
    @Path("signIn/{login}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public User signIn(@PathParam("login") String login, @PathParam("password") String encodedPasswordStr){
        User user = null;
        try{
            //Convert the String value of the encoded password to byte array.
            byte[] encodedPassword = Base64.getDecoder().decode(encodedPasswordStr);
            //Decrypting and hashing the password sent from the client.
            String password = Hashing.cifrarTexto(new String(PublicDecrypt
                    .decode(encodedPassword)));
            user = super.signIn(login, password);
            if(user == null) throw new NoResultException();
        } catch (NoResultException ex){
            throw new NotAuthorizedException(ex);
        }
        return user;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
