package bluejack162.edu.swalleto.models;

/**
 * Created by prk on 6/19/2017.
 */
public class User {

    String userId;
    String userName;
    String userEmail;
    String userPassword;

    public User(String userId, String userName, String userEmail, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
