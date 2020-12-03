package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.axe.R;

import java.util.List;

public class SeeMore extends AppCompatActivity {

    public static final String TAG = "TAG";
    String catatitle;
    TextView cataname;
   String id;
   RecyclerView seemorerecylerview;

List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        cataname=findViewById(R.id.seemoretextview);
        catatitle=getIntent().getStringExtra("cata");
        cataname.setText(catatitle);









    }
}