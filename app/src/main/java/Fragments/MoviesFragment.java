package Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import Model.AllMovies.PostMovies;
import Model.MovieAllCategory;
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
ProgressBar progressBar;
    List<MovieBannerMovies> moviesHomeBannerMoviesList;
    NestedScrollView nestedScrollView;
    RecyclerView MoviemainrecyclerView;
    MoviesMainRecylerAdapter MovieMainRecylerAdapter;
    List<MovieAllCategory> MovieAllCategoryList;
    List<PostMovies> listofcatgeory;
    TabLayout tabIndicator, categoryTab;
    String ACCESS_TOKEN;
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
//
                    Log.d("onResponse",listofcatgeory.toString());
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
        moviesHomeBannerMoviesList.add(new MovieBannerMovies(1, "A BEAUTIFUL DAY", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner1.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        moviesHomeBannerMoviesList.add(new MovieBannerMovies(2, "BLACK-MAIL", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner2.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        moviesHomeBannerMoviesList.add(new MovieBannerMovies(3, "SUFNA", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/moviebanner3.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));

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