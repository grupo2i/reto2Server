package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matteo Fernández
 */
@NamedQueries({
    @NamedQuery(
            name = "getAllEvents", 
            query = "SELECT ev FROM Event ev"
    )
})
@Entity
@Table(name = "event", schema = "reto2G2i")
@XmlRootElement
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private String place;
    private Float ticketprice;
    private String description;
    private String profileImage;
    @ManyToOne
    private Club club;

    @ManyToMany(mappedBy="events", fetch = FetchType.EAGER)
    private Set<Artist> artists;
    
    @ManyToMany(mappedBy="events", fetch = FetchType.EAGER)
    private Set<Client> clients;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="event", fetch = FetchType.EAGER)
    private Set<Rating> ratings;

    
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
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

    public Set<Artist> getArtists() {
        return artists;
    }

    @XmlTransient
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

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

}
