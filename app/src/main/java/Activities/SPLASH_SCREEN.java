package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.axe.R;

import android.content.Intent;
import android.os.Handler;

public class SPLASH_SCREEN extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_l_a_s_h__s_c_r_e_e_n);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SPLASH_SCREEN.this, Login.class);
                startActivity(mainIntent);
                SPLASH_SCREEN.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}