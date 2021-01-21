package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.axe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.MovieGenereListViewAdapter;
import Adapter.TvGenereListViewAdapter;
import Model.MovieGenere.Datum2;
import Model.MovieGenere.MovieGenere;
import Model.TvShowGenere.Datum;
import Model.TvShowGenere.TvGenere;
import SessionManager.UserSession;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import Retrofit.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSeeMore extends AppCompatActivity {
    public static final String TAG = "TAG";
    String catatitle;
    TextView cataname;

    String id;
    RecyclerView seemorerecylerview;
    String ACCESS_TOKEN;
    String tvcatageory;
    List<Datum2> datumList2;
    MovieGenereListViewAdapter tvGenereListViewAdapter;
    ProgressBar progressBar1, progressBar2;
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    List<Datum2> datumList3;
    List<Datum2> datumList4=new ArrayList<>();
    private boolean isLoading = true;
    private int pastvisibleitem, visibleitemcount, totalitemcount, previous_total = 0;
    int currentPage, from, last_page, per_page, to, total;
    String nextPage_url, path, previouspage_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_see_more);
        progressBar1 = findViewById(R.id.moviegenereprogress1);
        progressBar2 = findViewById(R.id.movieendprogressbar);
        cataname = findViewById(R.id.movieseemoretextview);
        progressBar1.setVisibility(View.VISIBLE);
        Intent intent = getIntent();


        tvcatageory = intent.getStringExtra("item2");

        Searchclient(tvcatageory);
        // catatitle=getIntent().getStringExtra("cata");
        cataname.setText(tvcatageory);
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);


        datumList2 = new ArrayList<>();
        datumList3=new ArrayList<>();

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
        Call<MovieGenere> movieDataCall = Client.getmoviegenere("created_at", "asc", tvcatageory);
        movieDataCall.enqueue(new Callback<MovieGenere>() {
            @Override
            public void onResponse(Call<MovieGenere> call, retrofit2.Response<MovieGenere> response) {


                if (response.isSuccessful()) {

                    currentPage = response.body().getMovie3().getCurrentPage();

                    last_page = response.body().getMovie3().getLastPage();
                    nextPage_url = response.body().getMovie3().getNextPageUrl();
                    path = response.body().getMovie3().getPath();
                    per_page = response.body().getMovie3().getPerPage();
                    //previouspage_url = (String) response.body().getMovie3().getPrevPageUrl();
                    total = response.body().getMovie3().getTotal();
                    datumList2 = response.body().getMovie3().getDatum2List();
                    setRecylerView(datumList2);
                }

            }

            @Override
            public void onFailure(Call<MovieGenere> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });


    }

    private void setRecylerView(List<Datum2> datumList) {
        recyclerView= findViewById(R.id.movieseemorerecyleview);
        int columnnumber=3;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,columnnumber);
        recyclerView.setLayoutManager(layoutManager);
        tvGenereListViewAdapter = new MovieGenereListViewAdapter(this, datumList);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(tvGenereListViewAdapter);
        //recyclerView.scrollToPosition(currentPage);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("-----", "end");
                    progressBar2.setVisibility(View.VISIBLE);

                   // Log.d("post", String.valueOf(currentPage));
                    performePagination();


                }
//                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Log.d("-----", "top");
//                    progressBar2.setVisibility(View.VISIBLE);
//                    performePagination2();
//
//
//                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


            }
        });

    }

    private void performePagination2() {


        currentPage = currentPage - 1;
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
        Call<MovieGenere> movieDataCall = Client.getmoviegenere("created_at", "asc", tvcatageory);
        movieDataCall.enqueue(new Callback<MovieGenere>(){
            @Override
            public void onResponse(Call<MovieGenere> call, retrofit2.Response<MovieGenere> response) {


                if (response.isSuccessful()) {
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
                    datumList2 = response.body().getMovie3().getDatum2List();
                    setRecylerView(datumList2);
                }

            }

            @Override
            public void onFailure(Call<MovieGenere> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });
    }

    private void performePagination() {


        currentPage = currentPage + 1;
        recyclerView.scrollToPosition(currentPage);
        Log.d("CurrentPage", String.valueOf(currentPage));

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
        Call<MovieGenere> movieDataCall = Client.getmoviepage(currentPage,"created_at", "asc", tvcatageory);
        movieDataCall.enqueue(new Callback<MovieGenere>() {
            @Override
            public void onResponse(Call<MovieGenere> call, retrofit2.Response<MovieGenere> response) {


                if (response.isSuccessful()) {


                    //Log.d("api",response.body().getMovie3().getDatum2List().get(0).getTitle());
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
                    datumList2.addAll(response.body().getMovie3().getDatum2List());
//                    setRecylerView(datumList2);
                   //tvGenereListViewAdapter = new MovieGenereListViewAdapter(getApplicationContext(), datumList2);
                    progressBar1.setVisibility(View.INVISIBLE);
                    progressBar2.setVisibility(View.INVISIBLE);
                    ///recyclerView.smoothScrollToPosition();
                    tvGenereListViewAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(tvGenereListViewAdapter);
                    recyclerView.scrollToPosition(datumList2.size()-1);
                    //recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                   // recyclerView.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<MovieGenere> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });


    }
}