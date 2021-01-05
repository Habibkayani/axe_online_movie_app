package Activities;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
//import android.widget.SearchView;
import android.widget.Toast;

import com.example.axe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.SearchListViewAdapter;
import Adapter.TvsearchlistviewAdapter;
import Fragments.HomeFragment;
import Fragments.MoviesFragment;
import Fragments.TvShowsFragment;
import Model.Search.Body;
import Model.Search.Search;
import Model.TvSearch.Body1;
import Model.TvSearch.TvShowSearch;
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

public class SearchAble extends AppCompatActivity {
    int Position;
    private RecyclerView recyclerView;
    Button addtoteam, Searchfromlist;
    String search;
    EditText searchedit;
    String ACCESS_TOKEN;
    List<Body> bodyList;
    List<Body1> bodyList1;
    TvsearchlistviewAdapter tvsearchlistviewAdapter;
    SearchListViewAdapter searchListViewAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_able);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bodyList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBarxxxx);
//        searchedit = findViewById(R.id.searchedittext);
//        Searchfromlist = findViewById(R.id.searchinlistbtn);

        Intent intent = getIntent();
        Position = Integer.parseInt(intent.getStringExtra("position"));
        //Log.d("postion", String.valueOf(Position));
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);
        // listner();
    }

//    private void listner() {
//        Searchfromlist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                search = searchedit.getText().toString();
//                searchedit.clearFocus();
//                Searchfromlist.clearFocus();
//                Log.d("postion", search);
//                if (Position == 1) {
//                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request newRequest = chain.request().newBuilder()
//                                    .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
//                                    .build();
//                            return chain.proceed(newRequest);
//                        }
//                    }).build();
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .client(client)
//                            .baseUrl("https://axetv.net/api/v2/search/")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                    UserService Client = retrofit.create(UserService.class);
//                    Call<Search> SearchCall = Client.getSearch(search);
//                    SearchCall.enqueue(new Callback<Search>() {
//                        @Override
//                        public void onResponse(Call<Search> call, retrofit2.Response<Search> response) {
//
//                            if (response.isSuccessful()) {
//                                // Toast.makeText(getApplicationContext(),"Data Sucessfull integrate",Toast.LENGTH_LONG).show();
//
//                                bodyList = response.body().getBody();
//                                setBodyRecylerViewAdapter(bodyList);
//
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<Search> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//
//
//            }
//
//        });
//
//    }

    private void setBodyRecylerViewAdapter(List<Body> bodyList) {

        recyclerView = findViewById(R.id.lrecyler8);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        searchListViewAdapter = new SearchListViewAdapter(this, bodyList);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(searchListViewAdapter);
        searchListViewAdapter.notifyDataSetChanged();

    }
    private void setBodyRecylerViewAdapter2(List<Body1> bodyList1) {

        recyclerView = findViewById(R.id.lrecyler8);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        tvsearchlistviewAdapter = new TvsearchlistviewAdapter(this, bodyList1);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(tvsearchlistviewAdapter);
        tvsearchlistviewAdapter.notifyDataSetChanged();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu1, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //  SearchView searchView = (SearchView) menu.findItem(R.id.action_search1);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search1).getActionView();
        searchView.setIconified(false);
        searchView.setLayoutParams(new Toolbar.LayoutParams(Gravity.START));
        searchView.setLayoutParams(new ActionBar.LayoutParams(Gravity.LEFT));
        searchView.setQueryHint("Type Here To Search");
        // searchView.setBackground(getResources().getDrawable(R.drawable.bg_white_rounded));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Position == 1) {

                    if (query.isEmpty()) {


                        bodyList.clear();
                        recyclerView.notifyAll();
                        searchListViewAdapter.notifyDataSetChanged();

                    }
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
                            .baseUrl("https://axetv.net/api/v2/search/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService Client = retrofit.create(UserService.class);
                    Call<Search> SearchCall = Client.getSearch(query);
                    SearchCall.enqueue(new Callback<Search>() {
                        @Override
                        public void onResponse(Call<Search> call, retrofit2.Response<Search> response) {


                            if (response.isSuccessful()) {

                                // Toast.makeText(getApplicationContext(),"Data Sucessfull integrate",Toast.LENGTH_LONG).show();

                                bodyList = response.body().getBody();
                                setBodyRecylerViewAdapter(bodyList);


                            } else {

                                Toast.makeText(getApplicationContext(), "Please Try something else", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<Search> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
                else if(Position == 2){
                    if (query.isEmpty()) {


                        bodyList.clear();
                        recyclerView.notifyAll();
                        searchListViewAdapter.notifyDataSetChanged();

                    }
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
                            .baseUrl("https://axetv.net/api/v2/search/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService Client = retrofit.create(UserService.class);
                    Call<TvShowSearch> SearchCall = Client.getTvSearch(query);
                    SearchCall.enqueue(new Callback<TvShowSearch>() {
                        @Override
                        public void onResponse(Call<TvShowSearch> call, retrofit2.Response<TvShowSearch> response) {


                            if (response.isSuccessful()) {

                                // Toast.makeText(getApplicationContext(),"Data Sucessfull integrate",Toast.LENGTH_LONG).show();

                                bodyList1= response.body().getBody();
                                setBodyRecylerViewAdapter2(bodyList1);


                            } else {

                                Toast.makeText(getApplicationContext(), "Please Try something else", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<TvShowSearch> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
                else {

                    Toast.makeText(getApplicationContext(),"Wrong Postion",Toast.LENGTH_LONG).show();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                progressBar.setVisibility(View.VISIBLE);
                if (Position == 1) {

                    Log.d("Response", newText);
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
                            .baseUrl("https://axetv.net/api/v2/search/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService Client = retrofit.create(UserService.class);
                    Call<Search> SearchCall = Client.getSearch(newText);
                    SearchCall.enqueue(new Callback<Search>() {
                        @Override
                        public void onResponse(Call<Search> call, retrofit2.Response<Search> response) {


                            if (response.isSuccessful()) {

                                // Toast.makeText(getApplicationContext(),"Data Sucessfull integrate",Toast.LENGTH_LONG).show();

                                bodyList = response.body().getBody();
                                setBodyRecylerViewAdapter(bodyList);


                            } else {

                                Toast.makeText(getApplicationContext(), "Please Try something else", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<Search> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
                else if(Position == 2){

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
                            .baseUrl("https://axetv.net/api/v2/search/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserService Client = retrofit.create(UserService.class);
                    Call<TvShowSearch> SearchCall = Client.getTvSearch(newText);
                    SearchCall.enqueue(new Callback<TvShowSearch>() {
                        @Override
                        public void onResponse(Call<TvShowSearch> call, retrofit2.Response<TvShowSearch> response) {


                            if (response.isSuccessful()) {

                                // Toast.makeText(getApplicationContext(),"Data Sucessfull integrate",Toast.LENGTH_LONG).show();

                                bodyList1= response.body().getBody();
                                setBodyRecylerViewAdapter2(bodyList1);


                            } else {

                                Toast.makeText(getApplicationContext(), "Please Try something else", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<TvShowSearch> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }



                else {

                    Toast.makeText(getApplicationContext(),"Wrong Postion",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });


        //searchView.setIconified(false);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            switch (Position) {
                case 0:
                    /** Start a new Activity MyCards.java */

//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.aaa, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                    onBackPressed2();
                    finish();

                    break;
                case 1:
//                    MoviesFragment fragment1 = new MoviesFragment();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.aaa, fragment1, fragment1.getClass().getSimpleName()).addToBackStack(null).commit();
//                    finish();
                    /** AlerDialog when click on Exit */
                    onBackPressed();
                    finish();
                    break;
                case 2:
//                    TvShowsFragment fragment2 = new TvShowsFragment();
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.aaa, fragment2, fragment2.getClass().getSimpleName()).addToBackStack(null).commit();
//                    finish();
                    onBackPressed1();
                    finish();
                    break;

            }


        }
        return super.onOptionsItemSelected(item);

    }

    public void onBackPressed() {
        super.onBackPressed();
        MoviesFragment.onBackPressed();
    }

    public void onBackPressed1() {
        super.onBackPressed();
        TvShowsFragment.onBackPressed1();
    }

    public void onBackPressed2() {
        super.onBackPressed();
        HomeFragment.onBackPressed2();
    }
}