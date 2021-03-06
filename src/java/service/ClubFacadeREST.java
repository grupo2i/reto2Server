package service;

import entity.Client;
import entity.Club;
import exception.UnexpectedErrorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
 *
 * @author Martín
 */
@Stateless
@Path("entity.club")
public class ClubFacadeREST extends AbstractFacade<Club> {

    private static final Logger LOGGER = Logger.getLogger(ClubFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ClubFacadeREST() {
        super(Club.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Club entity) {
        
        try {       
            LOGGER.log(Level.INFO, "Starting method create on {0}", ClubFacadeREST.class.getName());
            entity.setPassword(Hashing.encode(PublicDecrypt.decode(entity.getPassword())));
            super.create(entity);
            
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Club entity) {
        try {
            LOGGER.log(Level.INFO, "Starting method edit on {0}", ClubFacadeREST.class.getName());
            entity.setPassword(Hashing.encode(PublicDecrypt.decode(entity.getPassword())));
            super.edit(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Starting method remove on {0}", ClubFacadeREST.class.getName());
            super.remove(super.find(id));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Club find(@PathParam("id") Integer id) throws NoContentException {
        Club club;
        try {
            LOGGER.log(Level.INFO, "Starting method find on {0}", ClubFacadeREST.class.getName());
            club = super.find(id);
            if (club == null) {
                throw new NoContentException("The club does not exist");
            }
            em.detach(club);
            club.setPassword(PublicCrypt.encode(club.getPassword()));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        return club;
    }
    /**
     * Gets all the registered Clubs.
     * @return A list of Clubs.
     */
    @GET
    @Path("getAllClubs")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Club> getAllClubs(){
        try{
            LOGGER.log(Level.INFO, "Starting method getAllClubs on {0}", ClubFacadeREST.class.getName());
            List<Club> clubs = super.getAllClubs();
            //Encoding all clients password with RSA.
            for(Club club:clubs) {
                //Checking the password is not null to avoid NullPointerException.
                if(club.getPassword() != null) {
                    em.detach(club);
                    //Encoding password with RSA.
                    club.setPassword(PublicCrypt.encode(club.getPassword()));
                }
            }
            return clubs;
        }catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        
       
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}