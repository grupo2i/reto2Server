package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains a Clients evaluation of an Event.
 * @see Client
 * @see Event
 * @author Aitor Fidalgo
 */
@Entity
@Table(name="rating", schema="reto2G2i")
@XmlRootElement
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
    @MapsId("clientId")
    @ManyToOne
    private Client client;
    /**
     * Event that has been rated.
     */
    @MapsId("eventId")
    @ManyToOne
    private Event event;
    /**
     * Brief commentary about an Event made by a User.
     */
    private String comment;
    /**
     * Numeric evaluation of an Event made by a User.
     */
    @NotNull
    private Integer rating;

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
    public Client getClient() {
        return client;
    }
    /**
     * Sets the User that made the rating.
     * @param client User that rated the Event.
     */
    public void setClient(Client client) {
        this.client = client;
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
    /**
     * @return The comment made by the User.
     */
    public String getComment() {
        return comment;
    }
    /**
     * Sets the value of the comment.
     * @param comment The comment made by the User.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * @return The numeric evaluation made by the User.
     */
    public Integer getRating() {
        return rating;
    }
    /**
     * Sets the value of the numeric rating.
     * @param rating Numeric rating made by the User.
     */
    public void setRating(Integer rating) {
        this.rating = rating;
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
