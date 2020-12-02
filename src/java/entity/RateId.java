/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author aitor
 */
@Embeddable
public class RateId implements Serializable{
    private Integer userId;
    private Integer eventId;

    
    public RateId(){
    }
    
    public RateId(Integer userId, Integer eventId){
        this.userId = userId;
        this.eventId = eventId;
    }
    
    
    public Integer getUserId() {
        return userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    
    
}
