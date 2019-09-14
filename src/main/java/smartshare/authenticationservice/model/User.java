package smartshare.authenticationservice.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import smartshare.authenticationservice.constant.UserRole;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Document(collection = "registered_users")
public class User implements Serializable {

    @Id
    private long Id;

    @NotNull
    private String userName;

    @NotNull
    @Field("email")
    @Indexed(unique = true)
    private String emailAddress;

    @NotNull
    private String encryptedPassword;

    private String userRole = UserRole.ApplicationUser.toString(); // can be intialized through constructor too. depends on scenario.

    public User(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.encryptedPassword = encryptPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return emailAddress;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    private String encryptPassword( String password){

        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bcryptEncoder.encode(password);
        System.out.println("encodedPassword " +encodedPassword);
        return encodedPassword;
    }


    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", userName='" + userName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
