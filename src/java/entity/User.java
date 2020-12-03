package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Superclass of all type of users, contains common attributes.
 * @author Aitor Fidalgo
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="user", schema="reto2G2i")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Defines the status of Users.
     */
    public enum UserStatus{
        ENABLED,
        DISABLED
    }
    /**
     * Defines all types of Users.
     */
    public enum UserPrivilege{
        ADMIN,
        CLIENT,
        CLUB,
        ARTIST
    }
    
    /**
     * Used to identify Users.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    /**
     * Unique name of the User in the system.
     */
    @NotNull
    private String login;
    /**
     * Email of the User.
     */
    @NotNull
    @Pattern(regexp="^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$")
    private String email;
    /**
     * Full and real name of the User.
     */
    @NotNull
    private String fullName;
    /**
     * Brief description the Users writes about themselves.
     */
    @NotNull
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
    @NotNull
    private String password;
    /**
     * Specifies the last time the User loged in into the system.
     */
    @NotNull
    private Timestamp lastAccess;
    /**
     * Specifies the last time the User chaged their password.
     */
    @NotNull
    private Timestamp lastPasswordChange;

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(UserPrivilege userPrivilege) {
        this.userPrivilege = userPrivilege;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Timestamp lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
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