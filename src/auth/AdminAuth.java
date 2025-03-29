package auth;

public class AdminAuth {
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "password";

    public static boolean authenticate(String username, String password) {
        return username.equals(ADMIN_USER) && password.equals(ADMIN_PASS);
    }
}
