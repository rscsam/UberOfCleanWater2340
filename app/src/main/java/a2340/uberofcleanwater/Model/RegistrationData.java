package a2340.uberofcleanwater.model;
import java.util.ArrayList;

/**
 * Created by ander_000 on 2/12/2017.
 */

public class RegistrationData {
    private static ArrayList<User> RegData = new ArrayList<>();

    /**
     * Adds a new user to the RegData. Checks that no user with the name already exists.
     * @param user - User to be added.
     * @return - true if the user is successfully added, false if otherwise.
     */
    public static boolean addUser(User user) {
        for (User u : RegData) {
            if (u.getUserName().equals(user.getUserName()))
                return false;
        }
        RegData.add(user);
        return true;
    }

    /**
     * Removes a user based on the name of the user.
     * @param userName - Username of the user being removed.
     * @return - true if the user is successfully removed, false if otherwise.
     */
    public static boolean removeUser(String userName) {
        for (User temp : RegData) {
            if (temp.getUserName().equals(userName)) {
                RegData.remove(temp);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a user with the given name exists.
     * @param userName - Username of the user being checked.
     * @return - true if a user with the given name is in the database, false if not.
     */
    public static boolean userExists(String userName) {
        for (User temp : RegData) {
            if (temp.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given username/password combination exists in the database.
     * @param userName - Username of the user being checked for.
     * @param pass - password of the user being checked for.
     * @return - true if the given username/password combination exists, false if not.
     */
    public static boolean checkPassword(String userName, String pass) {
        for (User temp : RegData) {
            if (temp.getUserName().equals(userName)) {
                return temp.checkPassword(pass);
            }
        }
        return false;
    }

    /**
     * Gets a user object from its username
     * @param username The username used to identify the user
     * @return the userobject with username or null if user does not exist
     */
    public static User getUserByUsername(String username) {
        if (userExists(username)) {
            for (User u : RegData) {
                if (u.getUserName().equals(username))
                    return u;
            }
        }
        return null;
    }

    /**
     * @return an instance of the RegistrationData class
     */
    public static RegistrationData getInstance() {
        return new RegistrationData();
    }

}
