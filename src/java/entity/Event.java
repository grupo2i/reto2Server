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
    private Set<MusicGenre> musicGenres;
    private Club clubs;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Artist> artists;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Client> clients;

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

    public Set<Artist> getArtists() {
        return artists;
    }

    public Set<MusicGenre> getMusicGenres() {
        return musicGenres;
    }

    public Club getClubs() {
        return clubs;
    }

    public Set<Client> getClients() {
        return clients;
    }

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

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public void setMusicGenres(Set<MusicGenre> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public void setClubs(Club clubs) {
        this.clubs = clubs;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

}
