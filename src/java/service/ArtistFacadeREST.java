package service;

import entity.Artist;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import security.Hashing;

/**
 *
 * @author Matteo
 */
@Stateless
@Path("entity.artist")
public class ArtistFacadeREST extends AbstractFacade<Artist> {

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
    public void create(Artist entity) {
        //Encode the password with hashing algorithm.
        entity.setPassword(Hashing.cifrarTexto(entity.getPassword()));
        super.create(entity);
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
        super.edit(entity);
    }

    /**
     * Removes the artist by id
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
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
    public Artist find(@PathParam("id") Integer id) {
        Artist artist = null;
        artist = super.find(id);
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
