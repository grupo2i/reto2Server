package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Martin Angulo
 */
@NamedQueries({
    @NamedQuery(
            name = "getEventsByClientId", 
            query = "SELECT e FROM Event e JOIN e.clients cli WHERE cli.id=:clientId"
    ),
    @NamedQuery(
            name = "getAllClients", query = "SELECT c FROM Client c"
            ),
    
})
@Entity
@Table(name="client", schema="reto2G2i")
@XmlRootElement
public class Client extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="client_event", schema="reto2G2i")
    private Set<Event> events;

    /**
     * @return the events
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}