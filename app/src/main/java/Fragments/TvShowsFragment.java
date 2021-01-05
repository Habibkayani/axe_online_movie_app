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
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(1, "SKULLS & ROSES", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgGbT4OBkcgMlbzV9nzJwBFSTvq9MVHkCkFA&usqp=CAU", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(2, "COMICSTAAN", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNEUVwpTtuTwzvjzVRWdyz3Qf9LZd6e4AuSQ&usqp=CAU", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
        tvShowsBannerMoviesList.add(new TvShowsBannerMovies(3, "UPLOAD", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFRUXGBgYGRgYGBofFxsXGhgdGBgXGxoaHSgiGR0lHR0XIjEhJSkrLi4uGB8zODMtNygtLisBCgoKDg0OGxAQGy0lHyUtLS0tLS0tLS0tLTEtLS0tLy0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAJYBUAMBIgACEQEDEQH/xAAbAAACAgMBAAAAAAAAAAAAAAAEBQIDAAEGB//EAEIQAAIBAgQDBQYEBQIFAwUAAAECEQADBBIhMQVBURMiYXGBBjKRobHwFELB0SNScuHxB2IVM4KSsiRDohYlU8LS/8QAGgEAAwEBAQEAAAAAAAAAAAAAAAECAwQFBv/EAC4RAAICAgEEAQIDCQEAAAAAAAABAhEDEiEEEzFBUSJxYeHwFCMyQoGRkqGxBf/aAAwDAQACEQMRAD8A9xqt7kVM0FiWrLJPVWVFWwntxWluyaUriTOlEWGrkXV2zZ4qGYNbqFup13J2jnMrKytGmBk1lDviQNtaicVHKs3kivZWrCqyh/xIrRvxvR3YhqwgmtTQjYmdq0b8VDzxK0YWagWNDjFjerBcB1FLuRl4YateSTNUCaibkVW16ocyki2ag6yKrFypLietSpRfkdNFDqw5UtvkzFO3vDrS/GAbg1hmxKuGaQn8ihrkGqXskiQJovsQTRNwZVgCuNYr8m7yV4Ofu5l30qVjEkVRxC8cxmhLeIO2+9c/h8G3lHQpithTi2pjWkvCsUjKFOp+lOvxQ6V6OBKrbOTJ5onFZUDcJ2Fa7QnaujgyJGq3qD3D9mt3cO+UNGkT6elS+fAylzQ7moXCRz/ahb11tdfl4VzyZokXsahNL715+poa453LMPvzrJyNEhuTVGIvwpgiaUvcPMk/f3zqKty+/iahyLUS/EYxCOtCZ1+W3LzqnEGOlUQeVYM2jRfecAkSdtKpa6OpqBbeRLHx2jfSqZ1qaNEe7ULj17sjcVcWqF0yK+nmlKLR4ceGc4l8TTLCXdaWY2wUckDSt4fEgafOvBuWOXPyd7ipR4OoVhWnuAUmTHeNRu4013/t6qkuTm7DsYXMdQ2Kx86bDn9/CgFvbk8gJ+MVRbcsd/Of7/pWb6iclXyaLEkNEuQMx9B1PTyqjtZM/OhsbiIIUToNf6j9elbW6AIPj+x/Wk5818DUfYclyrFuzp8KWfiK0b5B3p92haDHtOlVXblU4e/LCec6ek/flRN5Pv73o5kuA8MEN8g6aVZb4sVMESOtVvYHXyqprAEz86x/eR8Mv6X5D/xiv7pqOedjNLL2EMSh35TQZuOrZmzCOm+/rVdyX8yFovTOgdfGh2blOtC4bHM3METHKfTrW7jqeYjrVOcX4Fq15ClxEDUaVWbo8Y++tC3bwA0IPPTy8RQzYtSd4+P60bBqNLZAPSo4rFgdD60nHEUnc+GvPzjrNZ+NU84ExqJ9YAo34pBpyVcQw4cztSfFYRk1Gops95NYfXxGlCXsWo2PzH6GuacPZvCTXALgMd2bBj5RXS4Xilu4OX61xGNuGSarwmJKkRMDoaITlAqeNS5PQhi1E6n1I0oZsYs5lfKeeopTwBDiLotliFhmJUiYGmmnUrXSN7KrmVu1JAIkMoMjmJ5ecV1wWSatI5pKMXTLLzMVQ5WEgd6O6Z2PhM07DxoOVauvCmgTfrqS7fsw/iBuK4KRmQf1Lpr1InQGlNmyHBysCQJyyM28QCOdPDerneJYF+2VrGYNcblEK0El9dhzP0PPmzxb5ibY2vDB7A7S6tpRq3OJheZ9BXR8e4WHtjIAGQQvio/Kf3rfCOEjDgy+d2OrRHd5KBOnU9fSjXuVcMVRal5ZMp/VweZXbhn3Y12nX1H3FV3VaNfqaZ+12Chw42Jg/wBXX1Gv+aRJg/Hz5Vxyg0zrjJNBGIvgoFIEgyG5kHcHry1qi3uBmEn5eNAYh4JjUdanYSEL7kiB4cyaz0NBravgNAAgCZ5nz+H0rTYgGAIk6ADXU76ffOkwxTQ3jvRuARnlmOURAMdenWpcaGkex28apYL47zWrfEVO0CkC3zGsDxUtM+Rn5UVaxqSNDGneGYg+O51+FesssjzXjRZxW5JnrzpPm1rolxCH8yRtqpmfODSzimHtashg9J/Q1z5sO31WbYp1wVWr8+cRp9a3ev6HUUsN2COtVG6eoma5XGjfyMTflQNevrr66frU7J1k6gSST4bab9PjSo3tzO1auYo5CJ3ifCDNNMTQQMSScx6z8ZP1ohGLHQefIdfuaBwQUgk+n+ToBrR6W2fQd0b6gjfpI8PPypxgwk0T7ZQJILRv0HT51W/ECdBp5UX2P8MpKk8zsAN5mAPs0gB8vjVSi14JjTG3DrnfHiaeC5GjaHziuWwpJOmWRuSwAjzmmXaoPedekZgTrz3q8dxRORWxlcvr4+dC4vECN53+Y8qVYzFgL3XnwAP1j9aAuPPOfOf1olJvgIwrkef8RIAGk5QOU/Df0oW9iVPv7HfQ7+ulKYJ0Any1rTYZ/wCRv+0gfGpqTKqKDHxdpRz6aMP81C9xBY7uv9RJ+gj50CbJG+X1ZfpM1C5H86j/ALv0FGjC0WXuI76g/Ghn4m24iaquBf5j6L+5FQVVPP4mPoDT7bDZFl3iDEDefv41VexZ5fP/ADWOqD86f/P/APkVUzr1+X7mn2w3IviWPnULeIadakbg+4rRejthuUu7TMGt4a/lmRoamWqF0jQkSOY/Sh4yozt0dn7DFQ3d5gn5gRNdvduaCvNeE8YtWHDhSVAgqsFgNy0c9dYnT5V3K4tboV0MoyqwOolW1Bg6iQRvW3SP93/Ux6qNZCXFL5WzcYbhTl0J15aDekacQOknvQJ0jUjYE6NHOKu9psWRagECepgRzGadOnXWuUucTVjBIOXWJnbQmNJGp5c6c5XKl6JhD6bOnbG/fjU7WPysG6fZHwrlcFeW5cIV8uY/ylpkBcy+MQI0Ec+Vddg7Fu3cH54lcx1GaRrpoOXiNdaIpsJJIc3NTvAqt7Y/m+VAXscASs6idIgx1E7jxpN7S+0wwtqdDcbRFP8A5H/aPnt5atr2ZqLfCLeO4U3HNpO8xUNA3EHQsdl5c64zimFv2hDoVnYkmNN6J9guMxdxF28//t52Y/1qP2AA8AKr4vxB8Tc7V2KIPcTou8nX3jpJ9Nq55RTVnRG4uvQuxCwFUkHm2m3WD8qrw+I/KpImcu0+vTagr7jMQssCdNNT8KNslUMtGYDKY1C+o571GhrtwbOG5axOs6emv5o1q9cUXhcvLroANN+XKtW74YHWFHMxJPhO2v8Aagbt1UkLP60u2Gx6YLvl6/5qWVTrGtKkxNEJiPGuxRONjAWh/M48nb96xrIiCznzdo+ExQoxAqzB4hXupbJ95gNIn9aeiC2KsccjxQ9y9zq/2lslWQqp7yByCQ0MSwKh1ADbDarOBcIS7ZNy5mXK9wM4YBLarbDhmBBzSTGhFcrxNypHSppRti0XY5z1++dSbEfLU8qWYrHKWC2QTqO8dyeUD8onrr5U09qMGti9kRiVZNzHvBmtuJjqpqFjbVmjaToNwd/QMfMDkPLxjnRYxgG9VcCwtq7bXPnlry2BlIgTbLAkEGdvnXO3caZrfTVIwvZs6HFcUGWBoKVLiATS7OzAmQB4kD6mun9ouDWcOARnX+IUAdlOZcubOAqyBOmtJwcuR7KPBVYxCgaUQ+PEe6Pi2vwalmEtI1q+4JJtqhHmzhSD10PhWcLdWuojglWYL3SAe8YB1B604wZLaD3x/RUH/SD/AOU1WeIvyIH9KqP/ABAobibIt10QMArMveYEypInQDeNqINu1+F7XK+ftOz94ZZyh80ZZiNImq0YtkVXca53dj5sf3oN7lVM9VM1GgbFrPVbXKqLVWW8apYyXIm92qjcqDPVeadqrti2LS1RzeI+/Kq8vjWGPE09A2LJHX5VZagmNSf6lX6jWhmudAB8/rWdmGGs+gn9RR2w2HA4ZdBH8ARrJZjpAme623y89JGx2HYFlJQZSFgECW1IjSeR1jTmdqBsY27a9x3VdtD88hMT60X/APUWaDdt27npDR5keewqXBlJgqSNDKkc9dPHTX5UZc4qe6XYE2oFtROogDNIECAB0mKou4uyZyLlJMwxYEaRpqRHn+1VWiVZXQDQyM2qkjYyCNulToXt7GD8Va4ci/m372x0JkNMaTtzjcGaptYSCqvayq8nttCdpABScsxOvoOZH/FkSXW02YliArDwhYViYkb9efIlcKty3mQ6ENIDDc5lIBGWSOmhheRqVipUhvJbtlfBcMly8GBJW13yAplhuoBU6ElY1idSNqL/APUsjgBkQtnaJU5ZgAE97Ivh4+EW4Jb9tRlAuqgjuiWA6ZVOYetBNxS3mkg5pPuvOvMZXysfIaUOE74Gpw9nW+z95TYRIkbAbzGmYdJidI36Um4p7MW7997j4hiNAEULKgcs2ojfTLz8aAwnHmYXLKqiW+zLC4zMCAsaaMBM67wQIMya5niPtCzgKgOxBbcmSD0HToPpGuvBirt0dOowHD3M3HvMR/yjlbUHQtAUCOh84NcnxPF3GcsFKiSQI2nWkrOZgyD4702vKrjbKeon6Ems+0ttvZspuqDbN0CD4dRMnzojiGL7QjRUGndUQogATHx+JpZbgaTI5SNflUkbfVee3SdN6egthlca2HyWyzpyJ0JPXKQI5afOl2LJB7wPrVYuGeQ+H1rMdfYoAYhZA0E6mYPM01ANkdmmLq5cZXKtj46zyA3Pw2q5MbAhhB6VrRjR0yYpm91tBpI6+caDx18udF8Oui2y3FKyjTqSRpyMnXfrNcYvFwCdiOh66/ryqGL4x3SA2QGYywSNdiBEbmopl8HUcZ4+DACpKjKAohQJJiPMk9deVLLftNeC9mMrLNyQQSGFxAjKYI7sAHTmJ1rkreP3POtfjO99+dJxfkpNJUdDg7pturgrKMrCdQSpDCdRI02q/i/Hbl8L2r58heG/NDtmIJk6A6AchpXLHGtvV9m5mBYzHpUSWsTbDDu5FH7/APDqOFe1NzDpkQIe92gLrLK+XKGXWJieR3pQcaPGl6X83uqxiJ05Ex6a1fzKZWLKYMRA5QYknzHzp1krx/srXp0/43/j+YX+MkUZjeLNeuvdeMzmTlECYjr4UuxNhVAh8x/pZR/8gCfhVAuICMxA+Ovw1qN2pa0dC6LHLG8u/H2/M6DB8TZUuIIi4FDTvCsGEdNRV2GxhRlcR3CGE7SpkfMVzqYhSwW2Tz0g+cy0TtTCziBJRhDTlMsAJBI0BHXpXRwuDy2vY4v4ku7MTqxLGNpYkn0mat/Gt2XZaZc/aeObLl36RSa+UJ1zL3gNI158yJ8KNvALEMCTrGzDzFWlZDZYzVfgMC94kIPdEkkgKOkknnS/PT/2Q4ytpzbuBSjnQsBC3IygkkGAQSpPKR41ehLkBPgmSDcRlzBssiJIB5kiNau4bwc3Fe44ZEUNrI1cflHjNdvxDBkqdJU7231HodcvzHQDeuV4o9y1bYWAz8+xcklN5uJBlhrrqRUcpjtM5xsKRJ02J3G0xPj6TVBNKsXxe47HO7kjTKoK7a5YXQfLzqOG4g0w4I057/HWdeR671aE0NCw61osKozEjMAYmJggT5molqvUz2D7GOCH3EPLUfruPSmdzj2GYQ+EAJ5pdcR5KZrl3eqHvUahY7uHBnZsQvmLbD5FTVD2rUwt0meqR/8AsaSNeqPb0ajsZ3MLqRp9+dVGyy6gkHnv9RQqY4jn6cv7elXrjPEj6UtEPdosXHODLLPiN45wR67zROH4pA0GnRlBH/csEx5aUIb2x2Ph9aovXGLFg+/IgGNNANiB61LgUshdjGW4xY6ZonKTBMATrz0ofFKM0gZRAAAk+uvOhrmMUH+IjHxQn0lTH1NVfiJPdcP4NofhoT6CpcSlIvuXHGqFT1B28KpfiK7XbCj/AHBF+RAH1NV/iiujAjxifp41JMRO0FfQ6eMbb/KlQ9ibW7T6oungT+5irFEaDSPH+9CqRuFHmug+W5q7P4R50UGxaHI5j78q1+Oyf/jOo0bQmdPeiNP93jUQ3iPl+lZaFp3Q3GhJGbfWNxtOvjRQrGD461ElBOm2Vl+n+KTYy/mJIjyH7V2eP4Dhbys9i9bLMTBDd3kcukgEsDsNJiuIv8Ne1cYXBljrOU+TCRGo1mox5Iz48P4Zc4OPPlD3DslsHrzJ3+NLeJcUWdN/D70+vlSHEcSZzzA+f9qlhsa1uCuuVgwDZYJEGCDOYGBI6VtqZbF1zFxOtUriizQASTyEk/AV3ftx7OW7l3h72EVFxYCfw0ABYsGzkaCctw69E8Kb/wCpuFXDCycHZt22ZhZKWgi6uoNpmCjmQ41gmqUBPIebi06AF0ZQ2gkETVlm8ZnYA6kLMfH0+Ve0cQ9n8NexNnDui5MNbF5wBBuM38O0XYCSP4d9iJ1OWdN0Yv2OI3rFpbS2basxOUKWZWhV1VRkImdyPONTUXcZ5sl5QQTLf1fqOdMBxxj+VVKDuwNSSefWvR8JwuxhsBevXMPZvvYvXEPaAAMO0W3qxVzABJEg+lAYbgOHxeBu3LNlbF3DqYcQS2VC2VmXJ2kiR3gI0NRLDGXk1xdTLG7X64o85xHG7zam4R4DQfACh1vu8AsWkxEmK7b209kjYw2Du20zXLiZruxM9nbgAKI0Jbc8+elS/wBJuCJibrm93l7PMBCqAM6gtoN4+9aJVFDwp5Jc+Fy/sJQgG3LpPTpSrFsC051HgzKCPCCZr0XhGM4diMS9i5hSpuW2yZXBE25dsuVUyMy7ctOU1zvspg7NrjX4O/bW4M9y0Tcy3FLKe0VgMsAsEUc/fI51h02L+az0f/S6pcYVGq/XpnOYe+gM9oPRWPw0g/GmeF4nqWAYuebAZPGVmT8ae+x3srav8TvK4/hWybwthDlyXTnsIQwEA22Gmux2imvDuBWf+OthxZ/hAPiIJXsskAQLeT3RcYrEx3a6tEeR3HRx5xVyT3gs/wAgAnzI1NMMEVUabnnzppgsEMTxLEXEtWhZw7gMhULaKG52c6aRlR3On5ec1R7T8NNnFIVTLaurbuLCwoBbK69BBBMcgwq1FIhyZUb/AI1EXBXS+1F7DYN/w/4LtRcUXRcV1zqpZlGX+GZ92YJg7eNUe03AsNZ/DOrOovJ7picwymTJGUkOogkCfExVcE8j3g/tI34UAkNcVgkmfdJhSY3jQHyrfC8aMVaYMoBXKAQCDqujCdj46eFJ8HgsMFIhnETqTpBnYQKI4JiMMFZrJW3mChkHeCssjWCddSCAenWoaspMT8W9msRJZgHZmGqkiF0EkaZoHSPWktpLdu4PxIfJIGYLIjoYIgxPiJGnXqsfxi8uYM6gBQVJUQxGsA92NgCDOh9anxazadWZoEICTzymYmd+cAzy61DTRalYr9p+JWTkt2nW1ZQApCM5YP3id49D1HSubxONtTCFiOZjTlqf5dzpvUb+Da48EwmTMGjMFGuUsOc66ZhprOhpDing5Tc0B/KCAfEZoYctDFUmSx29yRIMjwoS5doBLjKJGoPP9J5+s+FYl4tsJjfeR58hVpktBDXaqa5VPaTsR6VWXp0KwoXakL9A5zWjcooVjIYk9a0cR6Ut7Wt9pRQbBr3fhQ95Qwg1WLlaL0qHsSt3HT3W06HUfuPQ1NMQjMAVCMepEExyaND4GPPWqGeoProRS1HsMmZgTMjlDDWd4P5hyrVvFaHN3fHdZ66ar051rhiF7by2trLBO5VgQqz1BESeRH8tSt4RoJIgDczyG/P5eXnUNUWnZZcvCAvUmfIbedAcQuwBy9YO8DWrrYlZHIwOUAz+v3tS3iTiQQf0+f3vQkDfBdaxRnNOX/dBB/77eh9YrouE8WvZTLJeU6ZHKMeZkbMa5a0uknTx2b0Yd00dgrSC7DKHQSdzDFVYiHt6mTGvntFTkqi8XkRi4etaL+NRqS2ydOf6df71qYnvn+l+ItYrA4VrjAvgnYf0gI1tCfDI49V8Kp/054hb4kuKNwBmt4xMRb/mCB+0tAnmO4Vjp515Hwb2txWEw97DWWC274OeVlu8uQlSdVJHMdBUfZb2nxOAdnw7BS65WDKGUgHMNDzB5+JoA9l9lOLLieK8VXPJC27aDaFslrbkf7c7TP8Avrz7/Tfgd63xKx2qXLeV2XVWANxNGUGIaIbYnauOwfELtu52tu463Ne+Ccxze9J5zJmetdJxH/ULHXraW2uLbCZoNpAjHMpV5IPOTMRJ1oA9Psce7DhuNxaqt4LjL5VW1Rg2JVRrrpBn0FCe1nH797gyYvB5VQ/8+2F2UnK6giNFuaHquu0g+b8N9orpwj8PkLYc5u6ozSGV99xqoNGez/tPdwtm7hreVrVwlWR1Vg2ZcjEaT3hA+FAHpntcL5wfDsqNdbsjnKIxg5LcHKoMTroSNudCf6WWriYt1a26fwtM6MoIW5bmJAmAfmKWYP26xtu2ttXTKiqom2CYAAEnnVd//US+WDlv4iq6AqiZYcqWEGZ1RPnXLGUcuS16/sevkxZek6Zxkktq98/aq9A3sHwvEXOLJcuWLi2rRvM7OjqsPbe2AMwiWZhoPHpXP+0nEFt8Yv4pDItYkMvnaIBHlKx5Uyx3tzxC4pX8RkBkEooVoaJ72pXYarBHWuQOBOkacuUfOulJJUjypSlN2+We6+2T2cNgr+Jtsc1+1YtBgSGYAFEMg69xmP8A070VwfEWfwv/ABNFUO2GOwUCZLsvdGp7WRNeR8R4w92zawl1gyYcDIugJCqUUkjUwhjXrQie0mJt4Y4JWAtZ1YIBJJNwXMofeJ1jnNCaatClFxk4vyj1Phns6rcIe2b1vDvixJuPEZSdBupaUDc//cMyNKJ43wr/AO3YUi6l98I1u21xNQyd22TuYMdmx15E8684xHtb+KW1bu5v4YOVYREE5cwknWAIHlAq/A+2LYW3ct2jZyPmzKxZ5MZQfdgGNxJ5bxqxHqftB7UJY4hh8NcsqUuKJukCUzM6pvyzAT0zTyrz/wD1US8ca3bXSEC5rBVAQiMO8CN31BDAzIjSIFcVxLjNziV5rmK/iOiKiqgIGUMxg5ectuYov2j9pMXirdsOAz2SYhRnyEZSDGpJIXNy0BoAhg8Upfs7qy5MjMXZGjUMi3CQDqNANOXh0eDxgOxmNPKOVcSMYt5IgEAzGsg9VNM8JjGRgpOcbTsfUE6+dAHY37i3bZttBU7ggHy0Pj60i4vjnUdkSMuVFgCAVTbnt1FaS5eJBCFBDQx1U5YGnI6tz0+FVNiMPc0a5mfXVUbWPAA67azS4YcoN4Gr3O3YEZuyEkiWkaKvkegj3QOorjsPbDkhtnbvQRrGxHqeVdXwrEnCO+e1cVWABzxME6MIkRJPPzHOjrfAMPcYPacqAsd0zHSQZmKAOf4ngxh0RoXJES4bfqAJXbTWDINJcM7se6Ll0FT+UJamNxrA16AV6FjrOcdi3edVOVgIExMwdhpGkjXcnbkHx6zEyfj/AI9aqFUKQEnD3AZnKZjsqjQeE8/770EXozHcSI2gDqd58BS4NVEMnmrU1GawUCJVo1qaygCYesqutzQUSLVGZMASTsK1NStuVIZfeBBE7SDI+dAHVYLh/ZqEEZiZLj+cQBGnurmIBPMk6TQOMYwV97KYJMTv3hO++8frTe7iRfti5bkgyNAJUkDOjSdD0iJlTSd8I6luYk+HU8/Xf5VkzVAt25z5/f39ik9494RpHjHPrsPWmV8GSSCD9SOdLXJz6chuOXXXlQgZNDl8J80n6o3yqy44yrqQRGjZk11JAyyhB66bmqUxBA7ykTvl09SNVPqKM/AMELgDLOsMAfIrJB+A5daba9hG/QuuXWgEqq9DkUcp6T0361A4w8wpjbSFnmSBEk6amh2YsZJnxNammSTuXMxkx6ARUW86jW6AJqDpoddvGm+HwqKha4ubWJLREjYgH50ssYd391T58h4k8hTBeFlFl4bXkTER/bfwoEGm72QHY9kGOhKGWA31ZidNN9tKJ4Nh2Ym5cncwD15t+nxpf22VgUVQwB3XYnYjlmA8NCfWm3DuH4m7ZGS5aTtLqWkzuc7O5UQvd5Z1J1mJImDWWaEpRqLOvo8uPFlU8iuvFfIZii2U5QCfEkCOsikrYLEGYKAeB67bim6ex+KKXLv4lWS2JYq77BLV1SsgHvLdQiQDo8gRrYfZDFgIVvRmuXLcm45XNZ7btJCoWP8AyHiASQyaSTGOPDPGuGjt6nrsHUSTnF8fijn04ffYSG2JHvRqDB+YozhnCmV89yNPdEzr1NS4bwTF3QxS6ilb5sMpdwVcCWcgKQE31OpytAJBo237KY8sE7W2Wa41sDtXklRdYP7sZW7C6BzkCQJq5wyyTVoxw5ukxyU9ZNr8VQlcO157gBKhiJHQd3l5ULi7iTsQee0fc0/x2Hu27rI1xUNt2twolcytBAkDNqOY9BXOcRssHJYaMzZTprrvHKt4qkkcGSe8nL5dk8PiEAKlF1I75LSuvgYj060QJnRAFkbwGOh72pn/ADS8HQj7+FFPxRydDA6DznlTJGF3i2KtgW1YAAaEKCSJPWf3qrtS4BuAltyCGEnXU7Ak+FBniDnkPhr86z8bc2mPIAH40ASuBSTctKFgAsC0yTuVBA18JPhR2FxSOpU688pOn0gxG5FKsLddXlTB2kGDrOx+96f8P7O22eFzBdmBILEy2uuuw2g60m3XA403yGYxy1sC8SFRZAie6HHIxzJ5DWSTS+zZs3GKowSZAzZomRsZGu/x05QTxfjZuhldEDZixZJAJiNdYIpGLupPqfj9/Gs4bVyqNJ62qd/J2gR7NnLkLqdyVaToIBDEKDED3ht4UruYkJkKZrTa6SJB5yFJgGR8NjvQN/i95lVXYsF0XMJA0AjXnoNxW7GIDqQzQRybaSNMoGhnU7dOtVj2S+ryTNxb4DMV7T3MhV1BaCFcbgkZZ84nUVzpxg5DXxroMXwbuSWVhPWDvuDsO7Pw8KQY7hdyyc2WVBmd420PhqBO06VoqM2gLEsWMmtW7hG9E20DglYUj8pOlUtbP3tTEWB6kGqmK2ppklwNZNRDVlAiVamtTWTQM3W5qFSFAyKkqwZTBpzg/aLYXVmNmG/6TSc1W6zSasadHc4NsJfgdsqH+X3WPh3oRvKSfCkVwWWyAXhbuMSNZyrBykuV1AJnkeu1IFEHnHgYPxINZaK5jOgjw/UGpoqxjlXMcyBoJkywQzPe7u3UQRPlTi/ctPhtbHZIZyvbLXNPdJYsY3CLE5t9NZobh/ELSk5pgiAbapI5A5GgEDoCCc3QateEMtxghxVpl0IlMl6ZGnZ3GROuqs2vKpasqLr2efE0ZhksgTcYk9BOnyqjDBc3eo8lByX5b9daok3bez+Wy7fH96puF8xKDswfTnzPOiPxY5MJrQcswCd4ztuI6k7CgR09g8PKKSjlsokntT34Ekd/+bmPSlV17Cm4YvFS10DWYtFh2W7GdJk77eJqv8NGUfZP3NTfDJHf0Qb77Dy1ppW6Bugf8bhNe5d1M7jltzn51XwrjbWny9reTDm6rstsgMcjZkYToHBCGeqjoKvxtjhxuP2d28qZu53M0pJjeCDlAJnmaquYbA/lv35kb2xos67c42Py5UPgDrhxi2ysAvFWS8c5EJDgjKG009zINNNABFat4/p/xQXFa5dUhFgF1umSpO5a609QxpLgsbYEIuMxkAd2NAsbCJOkdI28akcdYAP/AK7G5tp359N+Q5+FIY1wvEbFuVVeKIna9rpEtdyrLGfeMiddDAJ20H4v7RItuEOOW7mN0C8RkLOStxt5ANtro02LHqTQlziFloK43GCPe1nyKzBG7ddT6nEuYG7h27a/iHxIukIe8R+HzgloPdDFc2kxPwppWTKVB2BxODcG5iAzXbjm5caX1J1kEEAmZqHExhLty2bdu89tc2dVzZix92ZMhfER86Hwn4RoAu3oA3NsTppv+w5UDxLD4UMWt3HZ5UQwAEazqBq2i9N+dIoMfBYb8QhXDX+zCtmtsGylwJXvMZynWdeXjFJfae3bS/8AwrbWlKqcrTo2s5ZPu6af2qrFoWCjLO8QNBzOp8OpoARyoA2GJq+3eULzzT6RGv6VVZtnmGI8B9/WrbGGYmQOsSR5a0mgsqFzWmeDvSo1Aj+YjeOWn70puD49OlStXYidQJjbc8/H+1MDoFw5usALmZz1UAGNlBJ1jSNJOtSGHymCuxjUaxMEa9J08qXWroOx8SBy6U3wGIDjKQS51D8iOhA0XwOgPnuAVJYGnTn9SfjpHxrai3IzDfTTYGJzHyn5CrXJ0G24P7VW+inmdtp5a/L1+NIA/Ad1gVOYKNATtyAInQCWPXemWJvqZVFBMONToJVdAOZM7dZ60htlkMAgg6eOp8+fenwTTammHxCAgnmQAvMsCFkHpI38JoAT4rhOYkqoV4JYA90/7l/lkmAsnU6aRK+YJV5DDQyNZ6EV0AxbKQ3cIIHdA/6huRDe6o/qHTUniSWr9uSs3ApIZTsQJieY1+vOmmJo5d8PIkRtyoR7dMMfgrlk6gleTR3T+x8KEZwasjwUAVKK2RUYoEbrRrK0RQMwVsGtVlAG5rJrVZQBphVDCiGNUsKTGjSsatF4RqCPKCvllb96prDSodg5rAKyspDMqyxcymRWVlAzqEcQGjlMUt4nxRSrWwGk6SYj61lZQAlcQawGsrKAJrdPWpNdJPpFarKBEs2v3FYCZ3rKygAm1h2PP5mrXw2SGMGdvCsrKBll68csQI6ctI186DD5GzDUa7/flWVlAhiLzsSqLbU9YP150RbR1k3HzCIKgHzkGZmtVlAxRxHEo7d0EdZj9D96UKK1WUCLEaNavs3gIlZ10+larKBhmGv5bhXUgwNd5Gx+NOPjqSZ8f8VlZSAuLAqNN/01rFw4eSdzl1k8wB8jrWqygA7h+HnRtRBnXWTBEdIA9T0pmtkDbT9pmPmfjWVlAwlMOjJkKgqeRAjedq5X2l9nRYXtbTdyQCpmQTtB5jz1HU1lZTXkTXBzmY1hFZWVZkzQNYVrKygZqsmsrKAMisrdZQBqoFZrKygZBhUKyspAf//Z", "http://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/patallok.mp4"));
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