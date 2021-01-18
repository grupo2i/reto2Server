package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Superclass of all type of users, contains common attributes.
 *
 * @author Aitor Fidalgo, Martin Angulo
 */
@NamedQueries({
    @NamedQuery(
            name = "signIn",
            query = "SELECT us FROM User us WHERE us.login like :login AND us.password like :password"
    ),
    @NamedQuery(
            name = "getUserByLogin",
            query = "SELECT us FROM User us WHERE us.login like :login"
    )
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user", schema = "reto2G2i")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Used to identify Users.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    /**
     * Unique name of the User in the system.
     */
    private String login;
    /**
     * Email of the User.
     */
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$")
    private String email;
    /**
     * Full and real name of the User.
     */
    private String fullName;
    /**
     * Brief description the Users writes about themselves.
     */
    private String biography;
    /**
     * Two possible value enum that defines the Users status.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    /**
     * Enum that defines the type of User.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserPrivilege userPrivilege;
    /**
     * Credential of the User.
     */
    private String password;
    /**
     * Specifies the last time the User loged in into the system.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccess;
    /**
     * Specifies the last time the User chaged their password.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;
    /**
     * Name of the profile image of the User.
     */
    private String profileImage;

    /**
     * Ratings of an event made by a User.
     *
     * The relation was supposed to be between Client and Rating but due to an
     * Hibernate bug it can't be done. See more
     * <a href="https://discourse.hibernate.org/t/embededid-containing-a-foreign-key-of-an-entity-with-inheritance/2334">here</a>
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Rating> ratings;

    /**
     * @return Id of the User.
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
     * @return The login of the User.
     */
    public String getLogin() {
        return login;
    }
    /**
     * Sets the value of the login.
     * @param login The value.
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * @return The email of he User.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the value of the email.
     * @param email The value.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return The full name of the User.
     */
    public String getFullName() {
        return fullName;
    }
    /**
     * Sets the value of the full name.
     * @param fullName The value.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    /**
     * @return The biography of the User.
     */
    public String getBiography() {
        return biography;
    }
    /**
     * Sets the value of the biography.
     * @param biography The value.
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }
    /**
     * @return The status of the User.
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }
    /**
     * Sets the value of the user status.
     * @param userStatus The value.
     */
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    /**
     * @return The user privilege.
     */
    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }
    /**
     * Sets the value of the user privilege.
     * @param userPrivilege The value.
     */
    public void setUserPrivilege(UserPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }
    /**
     * @return The password of the User.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the value of the password.
     * @param password The value.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return The last access of the User.
     */
    public Date getLastAccess() {
        return lastAccess;
    }
    /**
     * Sets the value of the last access.
     * @param lastAccess The value.
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }
    /**
     * @return The last password change of the User.
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }
    /**
     * Sets the value of the last password change.
     * @param lastPasswordChange The value.
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }
    /**
     * @return The profile image name of the User.
     */
    public String getProfileImage() {
        return profileImage;
    }
    /**
     * Sets the value of the profile image name.
     * @param profileImage The value.
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    /**
     * @return The ratings made by the User.
     */
    @XmlTransient
    public Set<Rating> getRatings() {
        return ratings;
    }
    /**
     * Sets the value of the ratings.
     * @param ratings The value.
     */
    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + id + " ]";
    }

}
