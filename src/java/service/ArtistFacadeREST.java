package service;

import entity.Artist;
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

/**
 *
 * @author Martin, Matteo
 */
@Stateless
@Path("entity.artist")
public class ArtistFacadeREST extends AbstractFacade<Artist> {

    private static final Logger LOGGER = Logger.getLogger(ArtistFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "reto2ServerPU")
    private EntityManager em;

    public ArtistFacadeREST() {
        super(Artist.class);
    }

    /**
     * Creates the entity Artist
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Artist entity) throws UnexpectedErrorException {
        //Encode the password with hashing algorithm.
        entity.setPassword(Hashing.encode(entity.getPassword().getBytes()));
        try {
            LOGGER.log(Level.INFO, "Starting method create on {0}", ArtistFacadeREST.class.getName());
            super.create(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Edits artists
     *
     * @param entity
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    @Override
    public void edit(Artist entity) {
        try {
            LOGGER.log(Level.INFO, "Starting method edit on {0}", ArtistFacadeREST.class.getName());
            super.edit(entity);
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Removes the artist by id
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO, "Starting method remove on {0}", ArtistFacadeREST.class.getName());
            super.remove(super.find(id));
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Find artist by id
     *
     * @param id
     * @return the artist associated to that id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Artist find(@PathParam("id") Integer id) throws NoContentException {
        Artist artist;
        try {
            LOGGER.log(Level.INFO, "Starting method find on {0}", ArtistFacadeREST.class.getName());
            artist = super.find(id);
            if (artist == null) {
                throw new NoContentException("The artist does not exist");
            }
        } catch (UnexpectedErrorException ex) {
            throw new InternalServerErrorException(ex);
        }
        return artist;
    }

    /**
     * Gets all the registered Artists.
     *
     * @return A list of Artists.
     */
    @GET
    @Path("getAllArtists")
    @Produces({MediaType.APPLICATION_XML})
    @Override
    public List<Artist> getAllArtists() {
        return super.getAllArtists();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
