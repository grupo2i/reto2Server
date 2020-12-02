/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Matteo Fernández
 */
@Entity
@Table(name = "event", schema = "reto2G2i")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private String place;
    private Float ticketprice;
    private String description;

    private enum musicGenre {
        ROCK, POP, REGGAE, EDM, TRAP, RAP, INDIE, REGGAETON, OTHER;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Client> clients;
    @MapsId("clubId")
    @ManyToOne
    private Set<Club> clubs;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTicketprice(Float ticketprice) {
        this.ticketprice = ticketprice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public Float getTicketprice() {
        return ticketprice;
    }

    public String getDescription() {
        return description;
    }

}
