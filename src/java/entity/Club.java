package entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Martin Angulo
 */
@NamedQueries({
    @NamedQuery(name = "getAllClubs", query = "SELECT c FROM Club c"),
})
@Entity
@Table(name="club", schema="reto2G2i")
@XmlRootElement
public class Club extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String location;
    private String phoneNum;
    
    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Event> events;

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @return the events
     */
    @XmlTransient
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
