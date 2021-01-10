package entity;

import java.io.Serializable;
import java.net.URL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Defines social networks that can be owed by Artists.
 *
 * @see Artist
 * @author Aitor Fidalgo
 */
@Entity
@Table(name = "socialNetwork", schema = "reto2G2i")
public class SocialNetwork implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Used to identify socialNetwork profiles.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * URL of the social network profile of the owner.
     */
    private URL socialNetwork;
    /**
     * Artist who is owner of the social network profile.
     */
    @ManyToOne
    private Artist artist;

    /**
     * @return The id of the social network.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the value of the id.
     * @param id The value.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return The social network profile URL.
     */
    public URL getSocialNetwork() {
        return socialNetwork;
    }
    /**
     * Sets the value of the social network URL.
     * @param socialNetwork The value.
     */
    public void setSocialNetwork(URL socialNetwork) {
        this.socialNetwork = socialNetwork;
    }
    /**
     * @return The owner of the social network profile.
     */
    @XmlTransient
    public Artist getArtist() {
        return artist;
    }
    /**
     * Sets the value of the Artist, owner of the social network.
     * @param artist The value.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SocialNetwork)) {
            return false;
        }
        SocialNetwork other = (SocialNetwork) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SocialNetwork[ id=" + id + " ]";
    }

}
