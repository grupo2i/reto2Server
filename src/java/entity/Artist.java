/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Matteo Fernández
 */
@Entity
@DiscriminatorValue("ARTIST")
@Table(name = "artist", schema = "reto2G2i")
public class Artist extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> discography = new ArrayList<String>();
    private ArrayList<String> socialNetworks = new ArrayList<String>();
    private Set<MusicGenre> musicGenres;
    @ManyToMany
    private Set<Event> events;

    public void setDiscography(ArrayList<String> discography) {
        this.discography = discography;
    }

    public void setSocialNetworks(ArrayList<String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public void setMusicGenres(Set<MusicGenre> musicGenres) {
        this.musicGenres = musicGenres;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public ArrayList<String> getDiscography() {
        return discography;
    }

    public ArrayList<String> getSocialNetworks() {
        return socialNetworks;
    }

    public Set<MusicGenre> getMusicGenres() {
        return musicGenres;
    }

    public Set<Event> getEvents() {
        return events;
    }

}
