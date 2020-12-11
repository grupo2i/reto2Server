package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Martin Angulo
 */
@Entity
@Table(name="client", schema="reto2G2i")
@XmlRootElement
public class Client extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToMany
    @JoinTable(name="client_event", schema="reto2G2i")
    private Set<Event> events;

    /**
     * @return the events
     */
    @XmlTransient
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