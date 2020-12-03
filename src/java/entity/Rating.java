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
@Table(name="rating", schema="reto2G2i")
public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Compound id used to identify the rating.
     */
    @EmbeddedId
    private RatingId ratingId;
    /**
     * User that rated the Event.
     */
    @MapsId("userId")
    @ManyToOne
    private User user;
    /**
     * Event that has been rated.
     */
    @MapsId("eventId")
    @ManyToOne
    private Event event;

    /**
     * @return id of the event.
     */
    public RatingId getId() {
        return ratingId;
    }
    /**
     * Sets the id of the rating.
     * @param ratingId value to be set.
     */
    public void setId(RatingId ratingId) {
        this.ratingId = ratingId;
    }
    /**
     * @return The User that made the rating.
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the User that made the rating.
     * @param user User that rated the Event.
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * @return The Event that has been rated.
     */
    public Event getEvent() {
        return event;
    }
    /**
     * Sets the Event that has been rated.
     * @param event Event that has been rated.
     */
    public void setEvent(Event event) {
        this.event = event;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingId != null ? ratingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ratingId fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rating[ id=" + ratingId + " ]";
    }
    
}
