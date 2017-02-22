package a2340.uberofcleanwater.Model;
import java.util.ArrayList;
import a2340.uberofcleanwater.Model.User;

/**
 * Created by ander_000 on 2/12/2017.
 */

public class RegistrationData {
    private static ArrayList<User> RegData;

    public RegistrationData() {
        RegData = new ArrayList<>();
        RegData.add(new User("user", "pass"));
    }

    /**
     * Adds a new user to the RegData. Checks that no user with the name already exists.
     * @param user - User to be added.
     * @return - true if the user is successfully added, false if otherwise.
     */
    public boolean addUser(User user) {
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
    public boolean removeUser(String userName) {
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
    public boolean userExists(String userName) {
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
    public boolean checkPassword(String userName, String pass) {
        for (User temp : RegData) {
            if (temp.getUserName().equals(userName)) {
                if (temp.checkPassword(pass)) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

}
