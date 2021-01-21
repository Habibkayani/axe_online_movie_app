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

import com.bumptech.glide.Glide;
import com.example.axe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.SimilarMoviesAdapter;
import Adapter.TvGenereListViewAdapter;
import Model.AllMovies.Main;
import Model.AllTvshows.PostTVShows;
import Model.MovieGenere.Datum2;
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
    String tvcatageory;
    List<Datum> datumList;
    List<Datum2> datumList2;
    TvGenereListViewAdapter tvGenereListViewAdapter;
    ProgressBar progressBar1, progressBar2;
    NestedScrollView nestedScrollView;
    private boolean isLoading = true;
    private int pastvisibleitem, visibleitemcount, totalitemcount, previous_total = 0;
    int currentPage, from, last_page, per_page, to, total;
    String nextPage_url, path, previouspage_url;


    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        progressBar1 = findViewById(R.id.tvgenereprogress1);
        progressBar2 = findViewById(R.id.endprogressbar);
        cataname = findViewById(R.id.seemoretextview);
        progressBar1.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        tvcatageory = intent.getStringExtra("item");

        Searchclient(tvcatageory);
        // catatitle=getIntent().getStringExtra("cata");
        cataname.setText(tvcatageory);
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);


        datumList = new ArrayList<>();
        datumList2=new ArrayList<>();

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
        Call<TvGenere> movieDataCall = Client.gettvgenere("created_at", "asc", tvcatageory);
        movieDataCall.enqueue(new Callback<TvGenere>() {
            @Override
            public void onResponse(Call<TvGenere> call, retrofit2.Response<TvGenere> response) {


                if (response.isSuccessful()) {

                    currentPage = response.body().getMovies().getCurrentPage();

                    last_page = response.body().getMovies().getLastPage();
                    nextPage_url = response.body().getMovies().getNextPageUrl();
                    path = response.body().getMovies().getPath();
                    per_page = response.body().getMovies().getPerPage();
                    total = response.body().getMovies().getTotal();
                    datumList = response.body().getMovies().getData();
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
        int colum=3;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,  colum);
        recyclerView.setLayoutManager(layoutManager);
        tvGenereListViewAdapter = new TvGenereListViewAdapter(this, datumList);
        progressBar1.setVisibility(View.INVISIBLE);
        progressBar2.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(tvGenereListViewAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("-----", "end");
                    progressBar2.setVisibility(View.VISIBLE);
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
        Call<TvGenere> movieDataCall = Client.gettvgenere("created_at", "asc", tvcatageory);
        movieDataCall.enqueue(new Callback<TvGenere>() {
            @Override
            public void onResponse(Call<TvGenere> call, retrofit2.Response<TvGenere> response) {


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
                    datumList = response.body().getMovies().getData();
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

    private void performePagination() {


        currentPage = currentPage + 1;
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
        Call<TvGenere> movieDataCall = Client.gettvgenere("created_at", "asc", tvcatageory);
        movieDataCall.enqueue(new Callback<TvGenere>() {
            @Override
            public void onResponse(Call<TvGenere> call, retrofit2.Response<TvGenere> response) {


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
                    datumList = response.body().getMovies().getData();
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
}