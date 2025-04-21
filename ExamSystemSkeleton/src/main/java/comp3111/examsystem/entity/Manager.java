package comp3111.examsystem.entity;

public class Manager {
    // there are only ONE account for manager
    // static final username and password
    private static final String USERNAME = "mod";
    private static final String PASSWORD = "password";

    /**
     * function return true/false of is the password and username is correct
     * @author Wong Hon Yin
     * @param username username input for login as manager
     * @param password password input for login as manager
     * @return true for successful login, false for fail login
     */
    public static boolean authenticate(String username, String password) {
        return username.equals(USERNAME) && password.equals(PASSWORD);
    }
}
