package Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.axe.R;
import Animation.ProgressBarAnimation;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SPLASH_SCREEN extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_l_a_s_h__s_c_r_e_e_n);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressBar = findViewById(R.id.loginprogressBar);
        textView = findViewById(R.id.text_view);
        progressBar.setVisibility(View.VISIBLE);
        //RunAnimation();
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

//       private void RunAnimation ()
//       {
//          ProgressBarAnimation ans=new ProgressBarAnimation(this,progressBar,textView,0f,100f);
//               ans.setDuration(8000)
//               ;
//               progressBar.setAnimation(ans);
//        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
//        a.reset();
//        TextView tv = (TextView) findViewById(R.id.textViewloading);
////        tv.setTextColor(getResources().getColor(R.color.red)));
//        tv.clearAnimation();
//        tv.startAnimation(a);
      //}
    }
