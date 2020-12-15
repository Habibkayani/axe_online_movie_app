package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.example.axe.R;
import com.google.gson.Gson;


import android.widget.EditText;
import android.widget.Toast;

import Model.Data;
import Model.LoginRequest;
import Model.LoginResponse;
import Model.UserProfile;
import Retrofit.ApiClient;
import SessionManager.Config;
import SessionManager.UserSession;
import okhttp3.ResponseBody;
import retrofit2.Call;
import Retrofit.UserService;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.umairadil.androidlogin.models.LoginResponse;
//import com.umairadil.androidlogin.restclient.RestClient;
//import com.umairadil.androidlogin.restclient.RestMethods;


public class Login extends AppCompatActivity {
    Button login;
    EditText emailfield, passwordfield;
   String token1;
   UserSession userSession;

//    public static final String MyPREFERENCES = "MyPrefs" ;
//    public static final String Name = "nameKey";
//    public static final String Avator = "AvatorKey";
//    public static final String Password = "phoneKey";
//    public static final String Email = "emailKey";
//    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Builds HTTP Client for API Calls

        //Binds UI to Activity
        setContent();


        //doLogin();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              doLogin();

            }
        });
    }

    private void doLogin() {

        String email = emailfield.getText().toString().trim();
        String password = passwordfield.getText().toString().trim();

        if (email.isEmpty()) {

            emailfield.setError("Enter is Required");
            emailfield.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailfield.setError("Enter a valid Email");
            emailfield.requestFocus();
            return;
        }
        if (password.isEmpty()) {

            passwordfield.setError("Password Required");
            passwordfield.requestFocus();
            return;

        }
        if (password.length() < 6) {
            passwordfield.setError("Password Should be atleast 6 character long");
            passwordfield.requestFocus();
            return;


        }
        else{

            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUser_name(emailfield.getText().toString());
            loginRequest.setPassword(passwordfield.getText().toString());
            //SharedPreferences.Editor editor = sharedpreferences.edit();

            Call<LoginResponse> loginResponseCall = ApiClient.getUserService().Userlogin(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                        LoginResponse loginResponse = response.body();




//                    String e=loginResponse.getData().getUser().getEmail();
//                    String av=loginResponse.getData().getUser().getAvatar();
//                    String n=loginResponse.getData().getUser().getName();
//                    editor.putString(Name, n);
//                    editor.putString(Avator, av);
//                    editor.putString(Email, e);
//                    editor.commit();

                        //Log.d(("OnResponse",response));

                        //Log.d("OnResponse", Token);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                        new UserSession(getApplicationContext()).SaveCredentials(loginResponse.getData().getToken());
                                Intent intent = new Intent(Login.this,MainActivity.class);

                                startActivity(intent);


                            }
                        }, 700);

                    } else if(response.body().getStatus()!= 200) {
                        LoginResponse loginResponse = response.body();
                        Toast.makeText(Login.this,loginResponse.getMessage() , Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(Login.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });

        }

////////////////////////////////////


    }

    void setContent() {

        //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        login = findViewById(R.id.login_btn);
        emailfield = findViewById(R.id.emial_text_field_login);
        passwordfield = findViewById(R.id.text_field_Password_login);












    }



}