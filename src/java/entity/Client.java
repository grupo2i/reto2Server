/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Martin Angulo <martin.angulo at tartanga.eus>
 */
@Entity
@Table(name="client", schema="reto2G2i")
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
