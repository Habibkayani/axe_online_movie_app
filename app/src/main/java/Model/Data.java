package Model;

import android.content.Context;

import SessionManager.UserSession;

public class Data {
    Context context;

    String token;
    User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
