package Model;
import java.util.ArrayList;
import java.security.SecureRandom;
/**
 * Created by ander_000 on 2/12/2017.
 */

public class RegistrationData {
    private static ArrayList<User> RegData;

    public RegistrationData() {
        RegData = new ArrayList<>();
        RegData.add(new User("user", "pass"));
    }

    public boolean addUser(String user, String password) {
        for (User u : RegData) {
            if (u.getName().equals(user))
                return false;
        }
        RegData.add(new User(user, password));
        return true;
    }

    public boolean removeUser(String user) {
        User temp = new User(user, "");
        for (User name : RegData) {
            if (name.getName().equals(user)) {
                RegData.remove(name);
                return true;
            }
        }
        return false;
    }

    public boolean userExists(String user) {
        User temp = new User(user, "");
        for (User name : RegData) {
            if (name.getName().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPassword(String user, String pass) {
        User temp;
        for (User name : RegData) {
            if (name.getName().equals(user)) {
                temp = name;
                if (temp.checkPasword(pass)) {
                    return true;
                }
            }
        }
        return false;
    }

}
