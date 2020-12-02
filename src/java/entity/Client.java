/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Martin Angulo <martin.angulo at tartanga.eus>
 */
@Entity
@DiscriminatorValue("Client")
@Table(name="clients")
public class Client extends User implements Serializable {
    @ManyToMany
    private Set<Event> events;
    @OneToMany(cascade=CascadeType.ALL)
    private Set<Rating> ratings;

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

    /**
     * @return the ratings
     */
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
