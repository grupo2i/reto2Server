package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Martin Angulo <martin.angulo at tartanga.eus>
 */
@Entity
@Table(name="client", schema="reto2G2i")
@XmlRootElement
public class Client extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToMany
    @JoinTable(name="client_event", schema="reto2G2i", joinColumns = 
            @JoinColumn(name="clientId", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "eventId", referencedColumnName="id"))
    private Set<Event> events;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="client")
    private Set<Rating> ratings;

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

    /**
     * @return the ratings
     */
    @XmlTransient
    public Set<Rating> getRatings() {
        return ratings;
    }

    /**
     * @param ratings the ratings to set
     */
    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
}
