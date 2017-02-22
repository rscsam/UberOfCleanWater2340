package a2340.uberofcleanwater.Model;

/**
 * Created by ander_000 on 2/12/2017.
 */

import a2340.uberofcleanwater.Model.AccountType;

/**
 * Class which holds information about the user.
 */
public class User {
    private String name; //User's real name
    private AccountType type;
    private String userName;
    private String password;
    private String emailAddress;
    private String homeAddress;
    private String title;

    /**
     * Full constructor.
     * @param name - User's real name
     * @param type - User's account type
     * @param userName - User's login id
     * @param password - User's login password
     * @param emailAddress - User's registered email address
     * @param homeAddress - User's registered home address
     * @param title - User's title (optional)
     */
    public User(String name, AccountType type, String userName, String password, String emailAddress, String homeAddress, String title) {
        this.name = name;
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
        this.title = title;
    }

    /**
     * Old constructor
     * @param userName - User's login id
     * @param password - User's login password
     */
    public User(String userName, String password) {
        this("", AccountType.User, userName, password, "", "", "");
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
    public AccountType getType() {
        return type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
    public String getHomeAddress() {
        return homeAddress;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }

}
