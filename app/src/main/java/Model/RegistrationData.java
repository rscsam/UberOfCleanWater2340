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
        User temp = new User(user, password);
        if (!RegData.contains(temp)) {
            RegData.add(temp);
            return true;
        }
        return false;
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
        User temp = new User(user, pass);
        if (RegData.contains(temp)) {
            return true;
        }
        return false;
    }
}
