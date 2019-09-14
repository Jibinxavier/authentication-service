package smartshare.authenticationservice.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import smartshare.authenticationservice.constant.UserRole;


public class User {
    private String userName;
    private String emailAddress;
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
                "userName='" + userName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
