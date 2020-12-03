package entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Compound id for Rates form by a User and an Events id.
 * @see Rating
 * @see User
 * @see Event
 * @author Aitor Fidalgo
 */
@Embeddable
public class RatingId implements Serializable{
    /**
     * Id of the User that made the Rating.
     */
    private Integer userId;
    /**
     * Id of the Event that has been rated.
     */
    private Integer eventId;

    /**
     * Empty constructor.
     */
    public RatingId(){
    }
    /**
     * Constructor that sets the values sent as parameters.
     * @param userId Users id.
     * @param eventId Events id.
     */
    public RatingId(Integer userId, Integer eventId){
        this.userId = userId;
        this.eventId = eventId;
    }

    /**
     * @return The id of the User that made the rating.
     */
    public Integer getUserId() {
        return userId;
    }
    /**
     * Sets the id of the User.
     * @param userId Id of the User.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    /**
     * @return The id of the Eventthat has beenrated.
     */
    public Integer getEventId() {
        return eventId;
    }
    /**
     * Sets the id of the Event.
     * @param eventId Id of the Event.
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
