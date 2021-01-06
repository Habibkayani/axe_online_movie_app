package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.axe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.CastMoviesAdapter;
import Adapter.SimilarMoviesAdapter;
import Model.AllMovies.Main;
import Model.AllMovies.Movie2;
import Model.AllMovies.Data;
import Model.AllMovies.MoviesCast;
import Model.AllMovies.SimilarMovie;
import Model.MovieGenere.Datum2;
import Model.Search.Body;
import Model.TvShowGenere.TvGenere;
import SessionManager.UserSession;
import Retrofit.UserService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import player.PlayerActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoDetails extends AppCompatActivity {
    TextView textView;
    LinearLayout linearLayout;

    RecyclerView castRecylerview;
    CastMoviesAdapter castMoviesAdapter;
    List<MoviesCast> moviesCastList;
    RecyclerView similarRecylerview;
    SimilarMoviesAdapter simlarMoviesAdapter;
    List<SimilarMovie> simlarmovieList;
    String mName,mImage,mId,mFileUrl;
    ImageView movieImage,back;
    Button play,trailer,report;
    TextView moviename, rating, genere,tvdescription;
    String playlink,reportlink,trailerlink;
    String ACCESS_TOKEN;
    List<MoviesCast> moviesCasts;
    Integer Id;
    ProgressBar progressBar,bar;
    ConstraintLayout constraintLayout;
    List<SimilarMovie> similarMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        movieImage=findViewById(R.id.tv_detail_image1);
        moviename=findViewById(R.id.tv_detail_moviename1);
        rating=findViewById(R.id.tvrating);
        progressBar=findViewById(R.id.progressBar);
        bar=findViewById(R.id.videoprogressBar);
        genere=findViewById(R.id.tvgenres);
        tvdescription=findViewById(R.id.tvdescription);
        play=findViewById(R.id.tvplaybtn);
        trailer=findViewById(R.id.tvtralier);
        report=findViewById(R.id.tvreport);
        back=findViewById(R.id.tv_back);
        constraintLayout=findViewById(R.id.videodetail1);
        moviesCasts=new ArrayList<>();
        similarMovies=new ArrayList<>();


        //set data layout

        listener();
        // setmoviesdami();
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);


           // client();


        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Movie2 user = (Movie2) bundle.getSerializable("user");

        if(user==null){
            Intent intent=getIntent();
            Bundle b=intent.getExtras();
            Body body= (Body) b.getSerializable("id");

            if(body==null){

                Intent intent2 = getIntent();
                int iddd= intent2.getExtras().getInt("Iddd");
                tvGenere(iddd);
               // Log.d("id", String.valueOf(id));
            }
            else{

                Integer SearchId=body.getId();
                Searchclient(SearchId);
            }
        }
        else
        {
            Id = user.getArticleId();
            client(Id);

        }


    }

    private void tvGenere(int iddd) {


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
                .baseUrl("https://axetv.net/api/v2/get/article/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);
        Call<Main> movieDataCall=Client.getMoviesDetail(iddd);
        movieDataCall.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, retrofit2.Response<Main> response) {


                if (response.body().getStatusCode()==200){

                    //Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("MovieData",response.body().getMessage());



                    mImage=response.body().getData().getBackground();
                    Glide.with(getApplicationContext()).load(mImage).into(movieImage);
                    mName=response.body().getData().getTitle();
                    moviename.setText(mName);
                    String tvdescription1=response.body().getData().getDescription();
                    tvdescription.setText(tvdescription1);
                    String R=response.body().getData().getRating();
                    String g=response.body().getData().getGenre();
                    rating.setText(R);
                    genere.setText(g);
                    playlink=response.body().getData().getVideoLink();
                    trailerlink=response.body().getData().getTrailerLink();

                    moviesCasts=response.body().getData().getCast();
                    setMainRecyler(moviesCasts);

                    similarMovies=response.body().getData().getSimilarMovies();
                    setsimlarMainRecyler(similarMovies);
                }

            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });
    }

    private void Searchclient(Integer idd) {


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
                .baseUrl("https://axetv.net/api/v2/get/article/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);
        Call<Main> movieDataCall=Client.getMoviesDetail(idd);
        movieDataCall.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, retrofit2.Response<Main> response) {


                if (response.body().getStatusCode()==200){

                    //Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("MovieData",response.body().getMessage());


                    mImage=response.body().getData().getBackground();
                    Glide.with(getApplicationContext()).load(mImage).into(movieImage);
                    mName=response.body().getData().getTitle();
                    moviename.setText(mName);
                    String tvdescription1=response.body().getData().getDescription();
                    tvdescription.setText(tvdescription1);
                    String R=response.body().getData().getRating();
                    String g=response.body().getData().getGenre();
                    rating.setText(R);
                    genere.setText(g);
                    playlink=response.body().getData().getVideoLink();
                    trailerlink=response.body().getData().getTrailerLink();

                    moviesCasts=response.body().getData().getCast();
                    setMainRecyler(moviesCasts);

                    similarMovies=response.body().getData().getSimilarMovies();
                    setsimlarMainRecyler(similarMovies);
                }

            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });

    }

    private void client(Integer id) {



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
                .baseUrl("https://axetv.net/api/v2/get/article/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);
        Call<Main> movieDataCall=Client.getMoviesDetail(id);
        movieDataCall.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, retrofit2.Response<Main> response) {


                if (response.body().getStatusCode()==200){

                    //Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("MovieData",response.body().getMessage());



                    mImage=response.body().getData().getBackground();
                    Glide.with(getApplicationContext()).load(mImage).into(movieImage);
                    mName=response.body().getData().getTitle();
                    moviename.setText(mName);
                    String tvdescription1=response.body().getData().getDescription();
                    tvdescription.setText(tvdescription1);
                    String R=response.body().getData().getRating();
                    String g=response.body().getData().getGenre();
                    rating.setText(R);
                    genere.setText(g);
                    playlink=response.body().getData().getVideoLink();
                    trailerlink=response.body().getData().getTrailerLink();

                    moviesCasts=response.body().getData().getCast();
                    setMainRecyler(moviesCasts);

                    similarMovies=response.body().getData().getSimilarMovies();
                    setsimlarMainRecyler(similarMovies);
                }

            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });

    }


    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), PlayerActivity.class);
                i.putExtra("Movieplaylink",playlink);
                startActivity(i);
            }
        });
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PlayerActivity.class);
                i.putExtra("Movietrailerlink",trailerlink);
                startActivity(i);
            }
        });
    }

    private void setsimlarMainRecyler(List<SimilarMovie>  similarMovies) {
        similarRecylerview = findViewById(R.id.tvsimilarmoviesrecylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        similarRecylerview.setLayoutManager(layoutManager);
        simlarMoviesAdapter = new SimilarMoviesAdapter(this,  similarMovies);
        bar.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        similarRecylerview.setAdapter(simlarMoviesAdapter);
    }

    private void setMainRecyler(List<MoviesCast> moviesCasts) {

        castRecylerview = findViewById(R.id.Tvcastrecylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        castRecylerview.setLayoutManager(layoutManager);
        castMoviesAdapter = new CastMoviesAdapter(this, moviesCasts);
        castRecylerview.setAdapter(castMoviesAdapter);
    }
}