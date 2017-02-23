package a2340.uberofcleanwater.model;

/**
 * Created by ander_000 on 2/12/2017.
 */

/**
 * Class which holds information about the user, but only allows viewing of the user fields (excluding password).
 * Password can be checked against a given string.
 */

public class UserView {
    private User user;


    /**
     * Constructs a view from an existing user.
     * @param user - existing user with desired information.
     */
    public UserView(User user) {
        this.user = user;
    }

    /**
     * Constructs a user inside this class.
     * @param name - User's real name
     * @param type - User's account type
     * @param userName - User's login id
     * @param password - User's login password
     * @param emailAddress - User's registered email address
     * @param homeAddress - User's registered home address
     * @param title - User's title (optional)
     */
    public UserView(String name, AccountType type, String userName, String password, String emailAddress, String homeAddress, String title) {
        this.user.setName(name);
        this.user.setType(type);
        this.user.setUserName(userName);
        this.user.setPassword(password);
        this.user.setEmailAddress(emailAddress);
        this.user.setHomeAddress(homeAddress);
        this.user.setTitle(title);
    }

    /**
     * Old constructor for backwards compat
     * @param userName - User's login id
     * @param password - User's login password
     */
    public UserView(String userName, String password) {
        this("", AccountType.User, userName, password, "", "", "");
    }

    public String getName() {
        return user.getName();
    }

    public AccountType getType() {
        return user.getType();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getEmailAddress() {
        return user.getEmailAddress();
    }

    public String getHomeAddress() {
        return user.getHomeAddress();
    }

    public String getTitle() {
        return user.getTitle();
    }

    public boolean checkPassword(String password) {
        return password.equals(this.user.getPassword());
    }

}
