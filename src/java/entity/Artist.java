package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class Artist extends from user
 * @author Ander, Matteo
 */
@Entity
@Table(name="artist", schema="reto2G2i")
@XmlRootElement
public class Artist extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy="artist")
    private Set<SocialNetwork> socialNetworks;
    @Enumerated
    @ManyToOne
    private MusicGenreEntity musicGenre;
    @ManyToMany
    @JoinTable(name="artist_event", schema="reto2G2i")
    private Set<Event> events;


    public void setSocialNetworks(Set<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public void setMusicGenres(MusicGenreEntity musicGenre) {
        this.musicGenre = musicGenre;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }


    public Set<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public MusicGenreEntity getMusicGenre() {
        return musicGenre;
    }

    @XmlTransient
    public Set<Event> getEvents() {
        return events;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Artist)) {
            return false;
        }
        Artist other = (Artist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.artista[ id=" + id + " ]";
    }

}