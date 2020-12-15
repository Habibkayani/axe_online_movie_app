package SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.Key;

import Retrofit.ApiClient;

public class UserSession {

private final String sharedprofileName ="haccount";
private  Context _context;
private UserSession instance;

    public UserSession(Context context) {
        this._context = context;
    }

    public void SaveKeyVlaue(String key ,String value ){

        SharedPreferences preferences=_context.getSharedPreferences(sharedprofileName,Context.MODE_PRIVATE);
        preferences.edit().putString(key,value).apply();


    }
    public String GetKeyVlaue(String key){

        SharedPreferences preferences=_context.getSharedPreferences(sharedprofileName,Context.MODE_PRIVATE);
        return preferences.getString(key,null);


    }
    public  void SaveCredentials(String token){


        SaveKeyVlaue("access_token",token);

    }
}
