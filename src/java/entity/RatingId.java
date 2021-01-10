package entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Compound id for Ratings form by a User and an Events id.
 *
 * @see Rating
 * @see User
 * @see Event
 * @author Aitor Fidalgo
 */
@Embeddable
public class RatingId implements Serializable {

    /**
     * Id of the User that made the Rating.
     */
    private Integer clientId;
    /**
     * Id of the Event that has been rated.
     */
    private Integer eventId;

    /**
     * Constructs a RatingId with no attributes set.
     */
    public RatingId() {
    }

    /**
     * Constructs a RatingId with the specified clientId and eventId.
     *
     * @param clientId The specified clientId.
     * @param eventId The specified eventId.
     */
    public RatingId(Integer clientId, Integer eventId) {
        this.clientId = clientId;
        this.eventId = eventId;
    }

    /**
     * @return The id of the User that made the rating.
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Sets the id of the User.
     *
     * @param userId Id of the User.
     */
    public void setClientId(Integer userId) {
        this.clientId = userId;
    }

    /**
     * @return The id of the Eventthat has beenrated.
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * Sets the id of the Event.
     *
     * @param eventId Id of the Event.
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
