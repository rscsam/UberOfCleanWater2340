package a2340.uberofcleanwater.model;

/**
 * Class which holds information about the user
 *
 * @author Ryan Anderson
 * @author Sam Costley
 * @version 1.0
 * @since 2017-02-12
 */
public class User {
    private String name; //User's real name
    private final AccountType type;
    private final String userName;
    private final String password;
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
    public User(String name, AccountType type, String userName, String password, String emailAddress,
                String homeAddress, String title) {
        this.name = name;
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
        this.title = title;
    }

    /**
     * Sets the name of the user
     * @param name - User's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the user
     * @return the user's name
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the account type of the user
     * @return the user's account type
     */
    public AccountType getType() {
        return type;
    }


    /**
     * Returns the username of the user
     * @return the user's username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password of the user
     * @return the user's password
     */
    String getPassword() {
        return password;
    }

    /**
     * Sets the email address of the user
     * @param emailAddress - the user's email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Returns the email address of the user
     * @return the user's email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the home address of the user
     * @param homeAddress - the user's home address
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * Returns the home address of the user
     * @return the user's home address
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * Sets the title of the user
     * @param title - the user's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the user
     * @return the user's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks if the user has completed their profile
     * @return true if the profile is complete, false otherwise
     */
    public boolean isProfileComplete() {
        return !(name == null || emailAddress == null || title == null || homeAddress == null)
                && !(name.isEmpty() || emailAddress.isEmpty() || title.isEmpty() || homeAddress.isEmpty());
    }
}
