package Model.AllLogin;

public class LoginRequest {
    private String email;
    private String password;

    public String getUser_name() {
        return email;
    }

    public void setUser_name(String user_name) {
        this.email = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
