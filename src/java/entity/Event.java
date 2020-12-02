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
import javax.persistence.Temporal;

/**
 *
 * @author Matteo Fernández
 */
@Entity
@SuppressWarnings("ValidAttributes")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private String place;
    private Float ticketprice;
    private String description;
    private enum musicGenre {
        ROCK, POP, REGGAE, EDM, TRAP, RAP, INDIE, REGGAETON, OTHER;

        public static musicGenre getROCK() {
            return ROCK;
        }

        public static musicGenre getPOP() {
            return POP;
        }

        public static musicGenre getREGGAE() {
            return REGGAE;
        }

        public static musicGenre getEDM() {
            return EDM;
        }

        public static musicGenre getTRAP() {
            return TRAP;
        }

        public static musicGenre getRAP() {
            return RAP;
        }

        public static musicGenre getINDIE() {
            return INDIE;
        }

        public static musicGenre getREGGAETON() {
            return REGGAETON;
        }

        public static musicGenre getOTHER() {
            return OTHER;
        }
    }
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Client> clients;
    @ManyToOne
    private Set<Club> clubs;
    
    public void setId(Long id) {
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

    public Long getId() {
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
