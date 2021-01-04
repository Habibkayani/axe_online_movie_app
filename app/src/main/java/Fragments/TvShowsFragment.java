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

import Adapter.TvShowsBannerMoviesPagerAdapter;
import Adapter.TvShowsMainRecylerAdapter;
import Model.AllTvshows.PostTVShows;
import Model.AllBanners.TvShowsBannerMovies;
import SessionManager.UserSession;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import Retrofit.UserService;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowsFragment extends Fragment {


    ProgressBar progressBar;
    TvShowsBannerMoviesPagerAdapter tvShowsBannerMoviesPagerAdapter;
    ViewPager bannerMovieViewPager2;
    String ACCESS_TOKEN;


    List<TvShowsBannerMovies> tvShowsBannerMoviesList;
    NestedScrollView nestedScrollView;
    RecyclerView TvShowmainrecyclerView;
    TvShowsMainRecylerAdapter tvShowsMainRecylerAdapter;

   // List<TvShowAllCategory> tvShowAllCategoryList;
    List<PostTVShows> listofcatgeory; ;

    TabLayout tabIndicator, categoryTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabIndicator = view.findViewById(R.id.tab_indicator2);
        categoryTab = view.findViewById(R.id.tabLayout);
        nestedScrollView = view.findViewById(R.id.nested_scroll);
        // appBarLayout = view.findViewById(R.id.appbar);
progressBar=view.findViewById(R.id.tvshowprogressBar);
        ////token
        UserSession userSession = new UserSession(getContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);




        //////////////////////////////////////////////////////////////////////////////////
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        // Toast.makeText(getContext(),ACCESS_TOKEN, Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://axetv.net//api/smart-phone/v1/get/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);
        Call<List<PostTVShows>> responseBodyCall = Client.getTvShows( "created_at", "asc");
        responseBodyCall.enqueue(new Callback<List<PostTVShows>>() {
            @Override
            public void onResponse(Call<List<PostTVShows>> call, retrofit2.Response<List<PostTVShows>> response) {

                if (response.isSuccessful() && response.body() != null) {


                    listofcatgeory = response.body();



//                    for(int i=0;i<response.body().size();i++){
//
//                        String catageoryname= response.body().get(i).getCategory();
//                       // List<Movie> movies=response.body().get(i).getMovie();
//
////                        for (int j = 0; j<catageoryname.length(); j++){
////
////
////                        };
//                     listofcatgeory.add(new PostTVShows(catageoryname));
//
//                    }
//
                      Log.d("onResponse",listofcatgeory.toString());
                      setMainrecyclerView(listofcatgeory);
//
//                }


                    /*
                     * We are getting the first category "Premier" from API
                     * Now loop through the array and get all the categories and movies
                     * I have just print first index in logcat.
                     * */

                }
            }


            @Override
            public void onFailure(Call<List<PostTVShows>> call, Throwable t) {
                Toast.makeText(getContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });


        setupmoviesbanner();

    }



    private void setMainrecyclerView(List<PostTVShows> homeAllCategoryList) {
        TvShowmainrecyclerView = getActivity().findViewById(R.id.main_recyler2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        TvShowmainrecyclerView.setLayoutManager(layoutManager);
       tvShowsMainRecylerAdapter = new TvShowsMainRecylerAdapter(getContext(), homeAllCategoryList);
       progressBar.setVisibility(View.INVISIBLE);
        TvShowmainrecyclerView.setAdapter(tvShowsMainRecylerAdapter);
    }
    public static void onBackPressed1()
    {
        //Pop Fragments off backstack and do your other checks
    }

    private void setupmoviesbanner() {


        tvShowsBannerMoviesList = new ArrayList<>();
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(1, "SKULLS & ROSES", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner1.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(2, "COMICSTAAN", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner2.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(3, "UPLOAD", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner3.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(4, "HUNTERS", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/tvshowbanner4.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        setHomeBannerMoviesPagerAdapter(tvShowsBannerMoviesList);

        //kids


//        kidsBannerMoviesList = new ArrayList<>();
//        kidsBannerMoviesList.add(new BannerMovies(1, "INSPECTOR CHINGUM", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner1.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
//        kidsBannerMoviesList.add(new BannerMovies(2, "ODDBODS", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner2.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
//        kidsBannerMoviesList.add(new BannerMovies(3, "BAJRANGI", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner3.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
//        kidsBannerMoviesList.add(new BannerMovies(4, "TENALI RAMAN", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner4.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
//        kidsBannerMoviesList.add(new BannerMovies(5, "WISHENPOOF", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/banners/kidsbanner5.png", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
//
    }

    private void setHomeBannerMoviesPagerAdapter(List<TvShowsBannerMovies> homeHomeBannerMoviesList) {
        bannerMovieViewPager2 = getActivity().findViewById(R.id.banner_viewPager2);
        tvShowsBannerMoviesPagerAdapter = new TvShowsBannerMoviesPagerAdapter(getContext(), homeHomeBannerMoviesList);
        bannerMovieViewPager2.setAdapter(tvShowsBannerMoviesPagerAdapter);
        tabIndicator.setupWithViewPager(bannerMovieViewPager2);
        Timer slideTimer = new Timer();
        slideTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        tabIndicator.setupWithViewPager(bannerMovieViewPager2, true);
    }

    class AutoSlider extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (bannerMovieViewPager2.getCurrentItem() < tvShowsBannerMoviesList.size() - 1) {
                        bannerMovieViewPager2.setCurrentItem(bannerMovieViewPager2.getCurrentItem() + 1);
                    } else {
                        bannerMovieViewPager2.setCurrentItem(0);
                    }
                }
            });
        }
    }
}