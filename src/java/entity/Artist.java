package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class Artist extends from user
 * @author Ander, Matteo
 */
@NamedQueries({
    @NamedQuery(name = "getAllArtists", query = "SELECT a FROM Artist a")
})
@Entity
@Table(name="artist", schema="reto2G2i")
@XmlRootElement
public class Artist extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy="artist", fetch = FetchType.EAGER)
    private Set<SocialNetwork> socialNetworks;
    @Enumerated(EnumType.STRING)
    private MusicGenre musicGenre;
    @ManyToMany
    @JoinTable(name="artist_event", schema="reto2G2i")
    private Set<Event> events;


    public void setSocialNetworks(Set<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void setMusicGenre(MusicGenre musicGenre) {
        this.musicGenre = musicGenre;
    }

    public Set<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    @XmlTransient
    public Set<Event> getEvents() {
        return events;
    }

    public MusicGenre getMusicGenre() {
        return musicGenre;
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
