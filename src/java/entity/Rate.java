package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * Contains a Clients evaluation of an Event.
 * @see Client
 * @see Event
 * @author Aitor Fidalgo
 */
@Entity
@Table(name="rate", schema="reto2G2i")
public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private RateId rateId;
    @MapsId("userId")
    @ManyToOne
    private User user;
    @MapsId("eventId")
    @ManyToOne
    private Event event;

    
    public RateId getId() {
        return rateId;
    }

    public void setId(RateId rateId) {
        this.rateId = rateId;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rateId != null ? rateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the rateId fields are not set
        if (!(object instanceof Rate)) {
            return false;
        }
        Rate other = (Rate) object;
        if ((this.rateId == null && other.rateId != null) || (this.rateId != null && !this.rateId.equals(other.rateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rate[ id=" + rateId + " ]";
    }
    
}
