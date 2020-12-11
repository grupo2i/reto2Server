package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author aitor
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.ArtistFacadeREST.class);
        resources.add(service.ClientFacadeREST.class);
        resources.add(service.ClubFacadeREST.class);
        resources.add(service.EventFacadeREST.class);
        resources.add(service.RatingFacadeREST.class);
        resources.add(service.UserFacadeREST.class);
    }
    
}
