package service;

import entity.Client;
import entity.Event;
import entity.User;
import exception.UnexpectedErrorException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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
import security.RandomPasswordGenerator;

/**
 * Defines REST services for Client entity.
 *
 * @see Client
 * @author Aitor Fidalgo
 */
@Stateless
@Path("entity.client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    private static final Logger LOGGER = Logger.getLogger(ClientFacadeREST.class.getName());

    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ClientFacadeREST() {
        super(Client.class);
    }

    /**
     * Persists a Client in the database.
     *
     * @param entity The Client to be persisted.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Client entity) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method create on {0}", ClientFacadeREST.class.getName());
            //Decoding password with RSA and encoding with SHA.
            entity.setPassword(Hashing.encode(PublicDecrypt.decode(entity.getPassword())));
            super.create(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * Persists a Client coming from the android client application in the database.
     * 
     * Android client application uses retrofit with SimpleXMLConverter which
     * cannot convert dates that is why the Client entity in the application takes
     * dates as Strings which then are converted to null by the server. That is
     * why it is needed a 'create' method that takes the current date of the clients
     * registration as a parameter and sets the clients lastAccess and
     * lastPasswordChange attributes before itis persisted.
     *
     * @param entity The Client to be persisted.
     * @param currentDate Current date of the clients registration.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @POST
    @Path("{currentDate}")
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Client entity, @PathParam("currentDate") String currentDate)
            throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method create on {0}", ClientFacadeREST.class.getName());
            //Formatter to convert the currentDate String into a Date.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Setting the attributes of the client that are dates with the given
            //formatted string representing the date of registration.
            entity.setLastAccess(formatter.parse(currentDate));
            entity.setLastPasswordChange(formatter.parse(currentDate));
            //Decoding password with RSA and encoding with SHA.
            entity.setPassword(Hashing.encode(PublicDecrypt.decode(entity.getPassword())));
            super.create(entity);
        } catch (ParseException | UnexpectedErrorException ex) {
            throw new InternalServerErrorException(new UnexpectedErrorException());
        }
    }

    /**
     * Updates a Client in the database with the specified data.
     *
     * @param entity Client with the updated data.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Client entity) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method edit on {0}", ClientFacadeREST.class.getName());
            Client client = find(entity.getId());
            //The password is already encoded with SHA.
            em.detach(client);
            //Encoding password with RSA.
            client.setPassword(PublicCrypt.encode(client.getPassword()));
            //Checking if the password of the client is encoded
            //with both SHA and RSA or only with RSA...
            if (new String(PublicDecrypt.decode(entity.getPassword()))
                    .equalsIgnoreCase(new String(PublicDecrypt
                            .decode(client.getPassword())))) {
                //Password is encoded with SHA and RSA.
                //Decoding with RSA to merge the password encoded with SHA.
                entity.setPassword(new String(PublicDecrypt.decode(entity.getPassword())));
            } else {
                //Password is encoded with RSA.
                //Decoding with RSA and encoding with SHA.
                entity.setPassword(Hashing.encode(PublicDecrypt.decode(entity.getPassword())));
            }
            super.edit(entity);
        } catch (UnexpectedErrorException | NoContentException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Removes a Client from the database.
     *
     * @param id Id of the Client to be removed.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method remove on {0}", ClientFacadeREST.class.getName());
            super.remove(super.find(id));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Fiends a Client in the database using its id attribute.
     *
     * @param id The id of the client.
     * @return The requested client.
     * @throws InternalServerErrorException If anything goes wrong.
     * @throws javax.ws.rs.core.NoContentException If the retrieved client is null.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Client find(@PathParam("id") Integer id) throws InternalServerErrorException, NoContentException {
        Client client;
        try {
            LOGGER.log(Level.INFO, "Starting method find on {0}", ClientFacadeREST.class.getName());
            client = super.find(id);
            if (client == null) {
                throw new NoContentException("The client does not exist");
            }
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        return client;
    }

    /**
     * Gets all the events a client has signed up for.
     *
     * @param id Id of the Client to get the Events.
     * @return A list of Events related to the specified Client.
     */
    @GET
    @Path("getEventsByClient/{id}")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Event> getEventsByClientId(@PathParam("id") Integer id)
            throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method getEventsByClientId on {0}", ClientFacadeREST.class.getName());
            return super.getEventsByClientId(id);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }

    }

    /**
     * Gets all the registered Clients.
     *
     * @return A list of Clients.
     */
    @GET
    @Path("getAllClients")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Client> getAllClients() throws InternalServerErrorException {
        try {
            LOGGER.log(Level.INFO, "Starting method getAllClients on {0}", ClientFacadeREST.class.getName());
            List<Client> clients = super.getAllClients();
            //Encoding all clients password with RSA.
            for(Client client:clients) {
                //Checking the password is not null to avoid NullPointerException.
                if(client.getPassword() != null) {
                    em.detach(client);
                    //Encoding password with RSA.
                    client.setPassword(PublicCrypt.encode(client.getPassword()));
                }
            }
            return clients;
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
        LOGGER.log(Level.INFO, "Starting method getUserByEmail on {0}", ClientFacadeREST.class.getName());
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
     * Updates the Client with the specified email with a random password
     * and sends an email to notify it.
     * 
     * @param email The specifies email.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Path("recoverPassword/{email}")
    public void recoverPassword(@PathParam("email") String email) throws InternalServerErrorException, NotAuthorizedException {
        try {
            LOGGER.log(Level.INFO, "Starting method recoverPassword on {0}", ClientFacadeREST.class.getName());
            Client client = (Client) getUserByEmail(email);
            //Getting random password.
            String randomPassword = RandomPasswordGenerator.getRandomPassword(10);
            //Encoding password with RSA.
            String encodedRandomPassword = PublicCrypt.encode(randomPassword);
            //Creating message to be sent by email.
            String message = "Your password has been updated to: " + randomPassword;
            //Sending email.
            EmailService.sendMail(email, "Password Recovery", message);
            //Updating client...
            client.setPassword(encodedRandomPassword);
            edit(client);
        } catch(MessagingException | UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        
    }
    
    /**
     * Updates the Client with the specified email with the specified password
     * and sends an email to notify it.
     * 
     * @param email The specifies email.
     * @param password The specified password.
     * @throws InternalServerErrorException If anything goes wrong.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Path("recoverPassword/{email}/{password}")
    public void changePassword(@PathParam("email") String email, @PathParam("password") String password)
            throws InternalServerErrorException, NotAuthorizedException {
        try {
            LOGGER.log(Level.INFO, "Starting method recoverPassword on {0}", ClientFacadeREST.class.getName());
            Client client = (Client) getUserByEmail(email);
            //Creating message to be sent by email.
            String message = "Your password has been updated to: " 
                    + new String(PublicDecrypt.decode(password));
            //Sending email.
            EmailService.sendMail(email, "Password Cange", message);
            //Updating client...
            client.setPassword(Hashing.encode(PublicDecrypt.decode(password)));
        } catch(MessagingException | UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * @return EntityManager instance used in the class.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
