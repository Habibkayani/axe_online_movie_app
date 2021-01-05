package Fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.axe.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Adapter.MovieBannerMoviesPagerAdapter;
import Adapter.MoviesMainRecylerAdapter;

import Model.AllBanners.MovieBannerMovies;
import Model.AllMovies.Movie2;
import Model.AllMovies.PostMovies;
import Model.AllTvshows.PostTVShows;
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
public class MoviesFragment extends Fragment {
    MovieBannerMoviesPagerAdapter movieBannerMoviesPagerAdapter;
    ViewPager bannerMovieViewPager1;
    ImageView Watch,Favourite;
ProgressBar progressBar;
    List<MovieBannerMovies> moviesHomeBannerMoviesList;
    NestedScrollView nestedScrollView;
    RecyclerView MoviemainrecyclerView;
    MoviesMainRecylerAdapter MovieMainRecylerAdapter;
    List<PostMovies> listofcatgeory;
    TabLayout tabIndicator, categoryTab;
    String ACCESS_TOKEN;
    List<Movie2> movieslist;
    ConstraintLayout constraintLayout;
    CardView cardView;
    int favourite,watch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabIndicator = view.findViewById(R.id.tab_indicator1);

        categoryTab = view.findViewById(R.id.tabLayout);
        nestedScrollView = view.findViewById(R.id.nested_scroll);
        progressBar=view.findViewById(R.id.movieprogressBar);
        movieslist=new ArrayList<>();
//        constraintLayout=view.findViewById(R.id.ad);
//        cardView=view.findViewById(R.id.cd);
        Watch=view.findViewById(R.id.watch);
        Favourite=view.findViewById(R.id.heart);


        //        Movie2 movie2= new Movie2();
//        Integer f=movie2.getArticleFavourite();
//        Integer w=movie2.getArticleViewed();
//        if(f==1){
//            Favourite.setVisibility(View.VISIBLE);
//
//        }
//        if(w==1){
//            Watch.setVisibility(View.VISIBLE);
//
//        }

        // appBarLayout = view.findViewById(R.id.appbar);

//        setupmoviesbanner();
//        setupmoviestvshowsandhome();
        ////token
        UserSession userSession = new UserSession(getContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);
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
                .baseUrl("https://axetv.net/api/smart-phone/v1/get/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);
        Call<List<PostMovies>> responseBodyCall = Client.getMovies( "title", "asc");
        responseBodyCall.enqueue(new Callback<List<PostMovies>>() {
            @Override
            public void onResponse(Call<List<PostMovies>> call, retrofit2.Response<List<PostMovies>> response) {

                if (response.isSuccessful() && response.body() != null) {

                   listofcatgeory = response.body();
//                    for(int i=0;i<listofcatgeory.size();i++){
//
//                        movieslist=listofcatgeory.get(i).getMovie2();
//
//                        for(int j=0;j<movieslist.size();j++){
//                            favourite=movieslist.get(j).getArticleFavourite();
//                            watch=movieslist.get(j).getArticleViewed();
//                            Log.d("Movies", String.valueOf(movieslist.get(j).getArticleFavourite()));
//                            if(favourite == 1){
//
//                                Favourite.setVisibility(View.VISIBLE);
//                            }
//                            if(watch == 1){
//
//                                Watch.setVisibility(View.VISIBLE);
//
//                            }
//
//                        }
//                    }

                 //   Log.d("onResponse",listofcatgeory.toString());
                    setMainrecyclerView(listofcatgeory);

                }
            }


            @Override
            public void onFailure(Call<List<PostMovies>> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });


        setupmoviesbanner();

    }
    public static void onBackPressed()
    {
        //Pop Fragments off backstack and do your other checks
    }
    private void setMainrecyclerView(List<PostMovies> listofcatgeory) {



        MoviemainrecyclerView = getActivity().findViewById(R.id.main_recyler1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        MoviemainrecyclerView.setLayoutManager(layoutManager);
        MovieMainRecylerAdapter = new MoviesMainRecylerAdapter(getContext(), listofcatgeory);
        progressBar.setVisibility(View.INVISIBLE);
        MoviemainrecyclerView.setAdapter(MovieMainRecylerAdapter);

    }

    private void setupmoviesbanner() {
        moviesHomeBannerMoviesList = new ArrayList<>();
        moviesHomeBannerMoviesList.add(new MovieBannerMovies(1, "A BEAUTIFUL DAY", "data:image/jpeg;base64,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", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        moviesHomeBannerMoviesList.add(new MovieBannerMovies(2, "BLACK-MAIL", "data:image/jpeg;base64,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", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        moviesHomeBannerMoviesList.add(new MovieBannerMovies(3, "SUFNA", "https://i.pinimg.com/originals/1a/3f/1b/1a3f1b8b1a4d95cd74fe0da7a79e1e6e.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));

        setHomeBannerMoviesPagerAdapter(moviesHomeBannerMoviesList);

    }

    private void setHomeBannerMoviesPagerAdapter(List<MovieBannerMovies> homeHomeBannerMoviesList) {
        bannerMovieViewPager1 = getActivity().findViewById(R.id.banner_viewPager1);
        movieBannerMoviesPagerAdapter= new MovieBannerMoviesPagerAdapter(getContext(), homeHomeBannerMoviesList);
        bannerMovieViewPager1.setAdapter(movieBannerMoviesPagerAdapter);
        tabIndicator.setupWithViewPager( bannerMovieViewPager1);
        Timer slideTimer = new Timer();
        slideTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        tabIndicator.setupWithViewPager( bannerMovieViewPager1, true);
    }

    class AutoSlider extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (bannerMovieViewPager1.getCurrentItem() < moviesHomeBannerMoviesList.size() - 1) {
                        bannerMovieViewPager1.setCurrentItem(bannerMovieViewPager1.getCurrentItem() + 1);
                    } else {
                        bannerMovieViewPager1.setCurrentItem(0);
                    }
                }
            });
        }
    }
}