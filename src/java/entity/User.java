package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author aitor
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="userPrivilege")
@Table(name="user", schema="reto2G2i")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum UserStatus{
        ENABLED,
        DISABLED
    }
    public enum UserPrivilege{
        ADMIN,
        CLIENT,
        CLUB,
        ARTIST
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull
    private String login;
    @NotNull
    @Pattern(regexp="^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*"
            + "(\\.[A-Za-z]{2,})$")
    private String email;
    @NotNull
    private String fullName;
    @NotNull
    private String biography;
    
    @NotNull
    private UserStatus userStatus;
    @NotNull
    private UserPrivilege userPrivilege;
    @NotNull
    private String password;
    @NotNull
    private Timestamp lastAccess;
    @NotNull
    private Timestamp lastPasswordChange;
    

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
