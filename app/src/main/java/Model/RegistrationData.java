package Model;
import java.util.HashMap;
/**
 * Created by ander_000 on 2/12/2017.
 */

public class RegistrationData {
    private static HashMap<String, String> RegData = new HashMap<>();

    public RegistrationData() {
        RegData.put("user", "pass");
    }

    public boolean addUser(String user, String password) {
        if (!RegData.containsKey(user)) {
            RegData.put(user, password);
            return true;
        }
        return false;
    }

    public boolean removeUser(String user) {
        if (RegData.containsKey(user)) {
            RegData.remove(user);
            return true;
        }
        return false;
    }

    public boolean userExists(String user) {
        if (RegData.containsKey(user)) {
            return true;
        }
        return false;
    }

    public boolean checkPassword(String user, String pass) {
        if (RegData.get(user).equals(pass)) {
            return true;
        }
        return false;
    }
}
