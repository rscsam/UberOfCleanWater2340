package Model;

/**
 * Created by ander_000 on 2/12/2017.
 */

public class User {
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public boolean checkPasword(String password) {
        if (password.equals(this.password)) {
            return true;
        }
        return false;
    }


}
