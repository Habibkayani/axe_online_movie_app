package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.axe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.SimilarMoviesAdapter;
import Adapter.TvGenereListViewAdapter;
import Model.AllMovies.Main;
import Model.AllTvshows.PostTVShows;
import Model.TvSearch.Body1;
import Model.TvShowGenere.Datum;
import Model.TvShowGenere.TvGenere;
import SessionManager.UserSession;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Retrofit.UserService;
public class SeeMore extends AppCompatActivity {

    public static final String TAG = "TAG";
    String catatitle;
    TextView cataname;
   String id;
   RecyclerView seemorerecylerview;
   String ACCESS_TOKEN;
   List<Datum> datumList;
   TvGenereListViewAdapter tvGenereListViewAdapter;
    ProgressBar progressBar1;


List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        progressBar1=findViewById(R.id.tvgenereprogress1);
        cataname=findViewById(R.id.seemoretextview);
        progressBar1.setVisibility(View.VISIBLE);
        Intent intent=getIntent();

       String tvcatageory=intent.getStringExtra("item");

        Searchclient(tvcatageory);
       // catatitle=getIntent().getStringExtra("cata");
        cataname.setText(tvcatageory);
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);


        datumList=new ArrayList<>();

    }

    private void Searchclient(String tvcatageory) {
       // Log.d("opoo", tvcatageory);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                            .build();
                    return chain.proceed(newRequest);
                }
            }).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://axetv.net/api/smart-phone/v1/get/genre/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UserService Client = retrofit.create(UserService.class);
            Call<TvGenere> movieDataCall=Client.gettvgenere("created_at","asc",tvcatageory);
            movieDataCall.enqueue(new Callback<TvGenere>() {
                @Override
                public void onResponse(Call<TvGenere> call, retrofit2.Response<TvGenere> response) {


                    if (response.isSuccessful()){
                        //progressBar.setVisibility(View.VISIBLE);
//                        //Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
//                        Log.d("MovieData",response.body().getMessage());
//
//
//
//
//
//                        similarMovies=response.body().getData().getSimilarMovies();
//                        setsimlarMainRecyler(similarMovies);
                        datumList=response.body().getMovies().getData();
                        setRecylerView(datumList);
                    }

                }

                @Override
                public void onFailure(Call<TvGenere> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.d("onResponse", t.getLocalizedMessage().toString());
                }
            });

        }

    private void setRecylerView(List<Datum> datumList) {
        RecyclerView recyclerView = findViewById(R.id.seemorerecyleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
     tvGenereListViewAdapter= new TvGenereListViewAdapter(this,  datumList);
       progressBar1.setVisibility(View.INVISIBLE);
      recyclerView.setAdapter(tvGenereListViewAdapter);
    }

}