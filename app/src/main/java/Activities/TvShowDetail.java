package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.axe.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Adapter.EpisodeRecylerViewAdapter;
import Adapter.SimilarTVShowsAdapter;
import Adapter.TvShowCastAdapter;

import Model.AllSearch.Body3;
import Model.AllTvshows.Episode;
import Model.AllTvshows.ModelTvShowDetail;
import Model.AllTvshows.Movie;
import Model.AllTvshows.Season;
import Model.AllTvshows.SimilarTvShows;
import Model.AllTvshows.TvShowsCast;
import Model.Search.Body;
import Model.TvSearch.Body1;
import Model.TvShowGenere.Datum;
import Retrofit.UserService;
import SessionManager.UserSession;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import player.PlayerActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    List<Episode> episodeItemList1;
    List<Episode> episodeItemList2;
    List<Episode> episodeItemList3;
    List<Episode> episodeItemList4;
    ProgressBar bar;

    RecyclerView TVShowcastRecylerview;
    TvShowCastAdapter castMoviesAdapter;

    List<TvShowsCast> tvShowsCasts;
    EpisodeRecylerViewAdapter episodeRecylerViewAdapter;
    RecyclerView EpisodeRecylerview;

    RecyclerView similarTVShowRecylerview;
    SimilarTVShowsAdapter simlarTVShowAdapter;
    List<SimilarTvShows> simlarTVShowList;
    TextView totalnumberepisode;
    String text;
    String ACCESS_TOKEN;
    ConstraintLayout constraintLayout;


    ///data fetching
    //String mName,mImage,mId,mFileUrl;
    ImageView movieImage, back;
    TextView tilte, rating, genere;
    TextView description;
    List<Episode> episodeList = null;
    List<Season> allitems = null;
    String SeasonTitle = null;
    Integer Seasonid = null;
    List<Season> seasonslist = null;
    List<String> spineerdata = null;

    ////Spinner//////
    AppCompatSpinner colorspinner;
    ArrayAdapter adapter;
    String trailerlink, report;
    Button Trailer, Report;
    Integer Id;
    //////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);
        ////token
        UserSession userSession = new UserSession(getApplicationContext());
        ACCESS_TOKEN = userSession.GetKeyVlaue("access_token");
        Log.d("Token", ACCESS_TOKEN);



        tvShowsCasts = new ArrayList<>();
        episodeList = new ArrayList<>();

        seasonslist = new ArrayList<>();
        allitems = new ArrayList<>();
        spineerdata = new ArrayList<>();
        constraintLayout = findViewById(R.id.tvshowdetail);


        ////fetch and set dat/////

        movieImage = findViewById(R.id.tv_detail_image1);
        tilte = findViewById(R.id.tv_detail_moviename1);
        totalnumberepisode = findViewById(R.id.textView5);
        rating = findViewById(R.id.tvrating);
        genere = findViewById(R.id.tvgenres);
        description = findViewById(R.id.tvdescription);
        back = findViewById(R.id.tv_back);
        Trailer = findViewById(R.id.tvtralier);
        Report = findViewById(R.id.tvreport);
        bar = findViewById(R.id.tvprogressBar);
        bar.setVisibility(View.VISIBLE);

//        mId=getIntent().getStringExtra("movieId");
//        mName=getIntent().getStringExtra("movieName");
//        mImage=getIntent().getStringExtra("movieImageUrl");
//        mFileUrl=getIntent().getStringExtra("movieFile");

        Intent ii= getIntent();
        Bundle bundlebundle = ii.getExtras();
        Body3 user1 = (Body3) bundlebundle.getSerializable("allsearchtvid");

        //Log.d("tvid",String.valueOf(videoid));
        if(user1!=null){
            int videoid=user1.getId();
            tvearchall(videoid);


        }
        else {
            Intent i = getIntent();
            Bundle bundle = i.getExtras();
            Movie user = (Movie) bundle.getSerializable("user");

            Intent intent2 = getIntent();


            // Integer TvGenere=Integer.valueOf(id);


            //setTvgere(TvshowGenreid);


            if (user == null) {
                Intent intent = getIntent();
                Bundle b = intent.getExtras();
                Body1 body = (Body1) b.getSerializable("id");

                if (body == null) {

                    Intent intent3 = getIntent();
                    int idddd = intent3.getExtras().getInt("Idddd");

                    if (String.valueOf(idddd) == null) {
                        Intent intent1 = getIntent();
                        Bundle bundle1 = intent1.getExtras();
                        Body3 body3 = (Body3) bundle1.getSerializable("allsearchtvarticleid");
                        int tvid = body3.getId();
                        Log.d("Videoid", String.valueOf(tvid));

                    } else {
                        Log.d("opoo", String.valueOf(idddd));
                        tvGenere(idddd);
                    }

                    // Log.d("id", String.valueOf(id));
                } else {

                    Integer SearchId = body.getId();
                    Searchclient(SearchId);
                }

            } else {
                Id = user.getId();
                client(Id);

            }


            // Toast.makeText(getApplicationContext(),String.valueOf(Id),Toast.LENGTH_LONG).show();


            //set data layout

            listener();


            ////////////////
            /////fetch data

        }
    }


    private void tvearchall(int videoid) {

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
                .baseUrl("https://axetv.net/api/v2/get/tv-show/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);

////////////////////////////////////////////////////////////////////////////
        Call<ModelTvShowDetail> listCall = Client.getTvShowDetail(videoid);
        listCall.enqueue(new Callback<ModelTvShowDetail>() {
            @Override
            public void onResponse(Call<ModelTvShowDetail> call, retrofit2.Response<ModelTvShowDetail> response) {
                // Log.d("ONRESPONSE",response.body().toString());


                /////get data from api//////
                String Title = response.body().getTitle();
                String Description = response.body().getDescription();
                String Avatar = response.body().getBackground();
                String Trailer = response.body().getTrailer();
                String Rating = response.body().getRating();
                Integer Favourite = response.body().getFavourite();
                Object Director = response.body().getDirector();
                String Genere = response.body().getGenre();
                trailerlink = response.body().getTrailer();

                Log.d("trailer", trailerlink);

                seasonslist = response.body().getSeasons();
                String spinner = null;
                for (int j = 0; j < seasonslist.size(); j++) {

                    spinner = seasonslist.get(j).getTitle();

                    spineerdata.add(spinner);
                }

                Log.d("ata", spineerdata.toString());

                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, spineerdata);
                adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                colorspinner.setAdapter(adapter);
                Log.d("teeee", spineerdata.toString());

//                    Seasonid = seasonslist.get(i).getId();
//                    SeasonTitle = seasonslist.get(i).getTitle();

                episodeList = seasonslist.get(0).getEpisodes();


                setBannerMoviesPagerAdapter(episodeList);


                int size = episodeList.size();
                totalnumberepisode.setText(String.valueOf(size));

                ////////////


                ///////////////////////////////////////
                ////set tvshowcastRecylerView/////////
                tvShowsCasts = response.body().getCast();
                setMainRecyler(tvShowsCasts);
                //////////////////////////////////////

                //set textview data and image////////
                tilte.setText(Title);
                description.setText(Description);
                //set data layout
                Glide.with(getApplicationContext()).load(Avatar).into(movieImage);
                rating.setText(Rating);
                genere.setText(Genere);


            }

            @Override
            public void onFailure(Call<ModelTvShowDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });
        ///////////////////////////////////////


        TVshowcast();

//                AppCompatSpinner colorspinner = findViewById(R.id.showseasonspinner);
//                ArrayAdapter adapter = ArrayAdapter.createFromResource(
//                        this, R.array.Spinner_Item, R.layout.color_spinner_layout
//                );
//                adapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
//
//                colorspinner.setAdapter(adapter);
//                colorspinner.setOnItemSelectedListener(this);
        colorspinner = findViewById(R.id.showseasonspinner);
        //colorspinner.setPopupBackgroundResource(R.drawable.spinner_background);


        colorspinner.setOnItemSelectedListener(this);
        colorspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                //text = selectedItem.toString();
                Log.d("teeee", selectedItem);


                for (int i = 0; i < seasonslist.size(); i++) {


                    Seasonid = seasonslist.get(i).getId();
                    SeasonTitle = seasonslist.get(i).getTitle();
//
//
                    if (SeasonTitle.equals(selectedItem)) {
                        episodeList = seasonslist.get(i).getEpisodes();
                        int size = episodeList.size();

                        totalnumberepisode.setText(String.valueOf(size));
                    }

                    Log.d("tee", SeasonTitle);
                }


                setBannerMoviesPagerAdapter(episodeList);


            }


            //
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void tvGenere(int idddd) {
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
                .baseUrl("https://axetv.net/api/v2/get/tv-show/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);

////////////////////////////////////////////////////////////////////////////
        Call<ModelTvShowDetail> listCall = Client.getTvShowDetail(idddd);
        listCall.enqueue(new Callback<ModelTvShowDetail>() {
            @Override
            public void onResponse(Call<ModelTvShowDetail> call, retrofit2.Response<ModelTvShowDetail> response) {
                // Log.d("ONRESPONSE",response.body().toString());


                /////get data from api//////
                String Title = response.body().getTitle();
                String Description = response.body().getDescription();
                String Avatar = response.body().getBackground();
                String Trailer = response.body().getTrailer();
                String Rating = response.body().getRating();
                Integer Favourite = response.body().getFavourite();
                Object Director = response.body().getDirector();
                String Genere = response.body().getGenre();
                trailerlink = response.body().getTrailer();

                Log.d("trailer", trailerlink);

                seasonslist = response.body().getSeasons();
                String spinner = null;
                for (int j = 0; j < seasonslist.size(); j++) {

                    spinner = seasonslist.get(j).getTitle();

                    spineerdata.add(spinner);
                }

                Log.d("ata", spineerdata.toString());

                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, spineerdata);
                adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                colorspinner.setAdapter(adapter);
                Log.d("teeee", spineerdata.toString());

//                    Seasonid = seasonslist.get(i).getId();
//                    SeasonTitle = seasonslist.get(i).getTitle();

                episodeList = seasonslist.get(0).getEpisodes();


                setBannerMoviesPagerAdapter(episodeList);


                int size = episodeList.size();
                totalnumberepisode.setText(String.valueOf(size));

                ////////////


                ///////////////////////////////////////
                ////set tvshowcastRecylerView/////////
                tvShowsCasts = response.body().getCast();
                setMainRecyler(tvShowsCasts);
                //////////////////////////////////////

                //set textview data and image////////
                tilte.setText(Title);
                description.setText(Description);
                //set data layout
                Glide.with(getApplicationContext()).load(Avatar).into(movieImage);
                rating.setText(Rating);
                genere.setText(Genere);


            }

            @Override
            public void onFailure(Call<ModelTvShowDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });
        ///////////////////////////////////////


        TVshowcast();

//                AppCompatSpinner colorspinner = findViewById(R.id.showseasonspinner);
//                ArrayAdapter adapter = ArrayAdapter.createFromResource(
//                        this, R.array.Spinner_Item, R.layout.color_spinner_layout
//                );
//                adapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
//
//                colorspinner.setAdapter(adapter);
//                colorspinner.setOnItemSelectedListener(this);
        colorspinner = findViewById(R.id.showseasonspinner);
        //colorspinner.setPopupBackgroundResource(R.drawable.spinner_background);


        colorspinner.setOnItemSelectedListener(this);
        colorspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                //text = selectedItem.toString();
                Log.d("teeee", selectedItem);


                for (int i = 0; i < seasonslist.size(); i++) {


                    Seasonid = seasonslist.get(i).getId();
                    SeasonTitle = seasonslist.get(i).getTitle();
//
//
                    if (SeasonTitle.equals(selectedItem)) {
                        episodeList = seasonslist.get(i).getEpisodes();
                        int size = episodeList.size();

                        totalnumberepisode.setText(String.valueOf(size));
                    }

                    Log.d("tee", SeasonTitle);
                }


                setBannerMoviesPagerAdapter(episodeList);


            }


            //
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                .baseUrl("https://axetv.net/api/v2/get/tv-show/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);

////////////////////////////////////////////////////////////////////////////
        Call<ModelTvShowDetail> listCall = Client.getTvShowDetail(id);
        listCall.enqueue(new Callback<ModelTvShowDetail>() {
            @Override
            public void onResponse(Call<ModelTvShowDetail> call, retrofit2.Response<ModelTvShowDetail> response) {
                // Log.d("ONRESPONSE",response.body().toString());


                /////get data from api//////
                String Title = response.body().getTitle();
                String Description = response.body().getDescription();
                String Avatar = response.body().getBackground();
                String Trailer = response.body().getTrailer();
                String Rating = response.body().getRating();
                Integer Favourite = response.body().getFavourite();
                Object Director = response.body().getDirector();
                String Genere = response.body().getGenre();
                trailerlink = response.body().getTrailer();

                Log.d("trailer", trailerlink);

                seasonslist = response.body().getSeasons();
                String spinner = null;
                for (int j = 0; j < seasonslist.size(); j++) {

                    spinner = seasonslist.get(j).getTitle();

                    spineerdata.add(spinner);
                }

                Log.d("ata", spineerdata.toString());

                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, spineerdata);
                adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                colorspinner.setAdapter(adapter);
                Log.d("teeee", spineerdata.toString());

//                    Seasonid = seasonslist.get(i).getId();
//                    SeasonTitle = seasonslist.get(i).getTitle();

                episodeList = seasonslist.get(0).getEpisodes();


                setBannerMoviesPagerAdapter(episodeList);
                int size = episodeList.size();

                totalnumberepisode.setText(String.valueOf(size));

                ////////////


                ///////////////////////////////////////
                ////set tvshowcastRecylerView/////////
                tvShowsCasts = response.body().getCast();
                setMainRecyler(tvShowsCasts);
                //////////////////////////////////////

                //set textview data and image////////
                tilte.setText(Title);
                description.setText(Description);
                //set data layout
                Glide.with(getApplicationContext()).load(Avatar).into(movieImage);
                rating.setText(Rating);
                genere.setText(Genere);


            }

            @Override
            public void onFailure(Call<ModelTvShowDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });
        ///////////////////////////////////////


        TVshowcast();

//                AppCompatSpinner colorspinner = findViewById(R.id.showseasonspinner);
//                ArrayAdapter adapter = ArrayAdapter.createFromResource(
//                        this, R.array.Spinner_Item, R.layout.color_spinner_layout
//                );
//                adapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
//
//                colorspinner.setAdapter(adapter);
//                colorspinner.setOnItemSelectedListener(this);
        colorspinner = findViewById(R.id.showseasonspinner);
        //colorspinner.setPopupBackgroundResource(R.drawable.spinner_background);


        colorspinner.setOnItemSelectedListener(this);
        colorspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                //text = selectedItem.toString();
                Log.d("teeee", selectedItem);


                for (int i = 0; i < seasonslist.size(); i++) {


                    Seasonid = seasonslist.get(i).getId();
                    SeasonTitle = seasonslist.get(i).getTitle();
//
//
                    if (SeasonTitle.equals(selectedItem)) {
                        episodeList = seasonslist.get(i).getEpisodes();
                        int size = episodeList.size();

                        totalnumberepisode.setText(String.valueOf(size));
                    }

                    Log.d("tee", SeasonTitle);
                }


                setBannerMoviesPagerAdapter(episodeList);


            }


            //
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void Searchclient(Integer searchId) {
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
                .baseUrl("https://axetv.net/api/v2/get/tv-show/detail/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService Client = retrofit.create(UserService.class);

////////////////////////////////////////////////////////////////////////////
        Call<ModelTvShowDetail> listCall = Client.getTvShowDetail(searchId);
        listCall.enqueue(new Callback<ModelTvShowDetail>() {
            @Override
            public void onResponse(Call<ModelTvShowDetail> call, retrofit2.Response<ModelTvShowDetail> response) {
                // Log.d("ONRESPONSE",response.body().toString());


                /////get data from api//////
                String Title = response.body().getTitle();
                String Description = response.body().getDescription();
                String Avatar = response.body().getBackground();
                String Trailer = response.body().getTrailer();
                String Rating = response.body().getRating();
                Integer Favourite = response.body().getFavourite();
                Object Director = response.body().getDirector();
                String Genere = response.body().getGenre();
                trailerlink = response.body().getTrailer();

                Log.d("trailer", trailerlink);

                seasonslist = response.body().getSeasons();
                String spinner = null;
                for (int j = 0; j < seasonslist.size(); j++) {

                    spinner = seasonslist.get(j).getTitle();

                    spineerdata.add(spinner);
                }

                Log.d("ata", spineerdata.toString());

                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, spineerdata);
                adapter.setDropDownViewResource(R.layout.custom_textview_to_spinner);
                colorspinner.setAdapter(adapter);
                Log.d("teeee", spineerdata.toString());

//                    Seasonid = seasonslist.get(i).getId();
//                    SeasonTitle = seasonslist.get(i).getTitle();

                episodeList = seasonslist.get(0).getEpisodes();


                setBannerMoviesPagerAdapter(episodeList);
                int size = episodeList.size();

                totalnumberepisode.setText(String.valueOf(size));

                ////////////


                ///////////////////////////////////////
                ////set tvshowcastRecylerView/////////
                tvShowsCasts = response.body().getCast();
                setMainRecyler(tvShowsCasts);
                //////////////////////////////////////

                //set textview data and image////////
                tilte.setText(Title);
                description.setText(Description);
                //set data layout
                Glide.with(getApplicationContext()).load(Avatar).into(movieImage);
                rating.setText(Rating);
                genere.setText(Genere);


            }

            @Override
            public void onFailure(Call<ModelTvShowDetail> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("onResponse", t.getLocalizedMessage().toString());
            }
        });
        ///////////////////////////////////////


        TVshowcast();

//                AppCompatSpinner colorspinner = findViewById(R.id.showseasonspinner);
//                ArrayAdapter adapter = ArrayAdapter.createFromResource(
//                        this, R.array.Spinner_Item, R.layout.color_spinner_layout
//                );
//                adapter.setDropDownViewResource(R.layout.spinner_drop_down_layout);
//
//                colorspinner.setAdapter(adapter);
//                colorspinner.setOnItemSelectedListener(this);
        colorspinner = findViewById(R.id.showseasonspinner);
        //colorspinner.setPopupBackgroundResource(R.drawable.spinner_background);


        colorspinner.setOnItemSelectedListener(this);
        colorspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                //text = selectedItem.toString();
                Log.d("teeee", selectedItem);


                for (int i = 0; i < seasonslist.size(); i++) {


                    Seasonid = seasonslist.get(i).getId();
                    SeasonTitle = seasonslist.get(i).getTitle();
//
//
                    if (SeasonTitle.equals(selectedItem)) {
                        episodeList = seasonslist.get(i).getEpisodes();
                        int size = episodeList.size();

                        totalnumberepisode.setText(String.valueOf(size));
                    }

                    Log.d("tee", SeasonTitle);
                }


                setBannerMoviesPagerAdapter(episodeList);


            }


            //
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        Trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PlayerActivity.class);
                i.putExtra("tvshowtrailerlink", trailerlink);
                startActivity(i);
            }
        });
//        Report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//            }
//        });
    }

    private void TVshowcast() {

        tvShowsCasts = new ArrayList<>();
//        tvShowsCasts.add(new TvShowsCast("https://thumbor.forbes.com/thumbor/711x477/https://specials-images.forbesimg.com/dam/imageserve/968210608/960x0.jpg?fit=scale"));
////        moviesCastList.add(new MoviesCast("idsb.tmgrup.com.tr/ly/uploads/images/2020/04/01/thumbs/1200x600/28185.jpg"));
////        moviesCastList.add(new MoviesCast("ei.marketwatch.com/Multimedia/2019/08/22/Photos/ZQ/MW-HP986_Dwayne_20190822132738_ZQ.jpg?uuid=239bf3ea-c502-11e9-842a-9c8e992d421e"));
////        moviesCastList.add(new MoviesCast("img1.looper.com/img/gallery/why-the-actor-who-played-jon-snow-hasnt-been-seen-since-got/intro-1591209105.jpg"));
//        tvShowsCasts.add(new TvShowsCast("https://www.thenews.com.pk/assets/uploads/updates/2020-10-16/730295_2774417_osman_updates.jpg"));
//        tvShowsCasts.add(new TvShowsCast("https://i.pinimg.com/originals/44/e0/17/44e0173aedc0acc2edee4707ef5cd6c9.jpg"));
//        tvShowsCasts.add(new TvShowsCast("data:image/jpeg;base64,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"));
//        tvShowsCasts.add(new TvShowsCast("data:image/jpeg;base64,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"));
//        tvShowsCasts.add(new TvShowsCast("https://www.thenews.com.pk/assets/uploads/updates/2020-10-16/730295_2774417_osman_updates.jpg"));
//        setMainRecyler(tvShowsCasts);

        simlarTVShowList = new ArrayList<>();
        simlarTVShowList.add(new SimilarTvShows("IO", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Moon Light", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Black Panther", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Star Wars", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Baby Driver", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Future", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTEhMWFhUXFxoXFRgWGBcYGBgYHRceFxgdHRcaHyggHRolGxgVITEhJSkrLi4uFx8zODMsNygtLisBCgoKDg0OGxAQGy0mICUtLS8tLS0tLS8tLS8tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIARkAswMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAAFBgIEAAMHAQj/xABCEAACAQIEBAQDBQUGBgIDAAABAhEAAwQSITEFBkFREyJhcYGRoQcUMkKxIzPB0fAVUmKCkuFDU2NyovEWcyQ0RP/EABkBAAMBAQEAAAAAAAAAAAAAAAABAgMEBf/EACwRAAICAgIABQMEAgMAAAAAAAABAhEDIRIxBBNBUYEiYXEyQqGx8PGRweH/2gAMAwEAAhEDEQA/AOP2cO9w5baM7a+VVLH10GtbzwfEDfD3hO02rn8vUfMVb5PusMUuTIWKsALmbK2mxy76A6GR8YIc8M+OtZStrDsUTICGYErFsdQDP7JTvGpraWWMXtpfJpGEpbSb+Dnz8Mvhgps3QxmF8N8xiZgRJiD8jWt+GXwCTZugKJYm24AETJMaCNadP7exbXEX7kD93LeQEjyusBSRClfxGAI8x9CNOM5mxXh3ExGEbKzXGmCgRXtvbYBjbIAAusc25OpmTJLZDQpNwy+ASbN0Afim28CBJnTTQg+xqvetMjFXUqw3VgQR11B12p0f7R3zBhYQMMwEsuzsjn8gIMoIII3ky3moNf5htXLrvcwdtwxBhnYOCEVB+0UAx5WMRHm9KlsADNe0Q4lj7FxQtrCrZIacy3HckQQQc3rBn0qkBVR2BAio1N68FDjsRiisIqVQoekMw1IVA16KlPYjZNerUBXoatkx2SmvC1QJqNS5hZPNWA1CsqeYiZNSmtc16pp8h2XEuGNz/XwrK1AV5WnJlkuHYjw7tu5P4XU/CdfpNdgv4W4xQo+UBgWBAIdY29Ohkdq4vdFd8wHDreLwFpXkC5aSSDBGgOnrXm+Mahxl969+zo8LOlKPuvx0CHUrfVcpi4h83TMpkD3ys5+FaLeLz4m7hbiAQgdNZzodGkeh0o9zALeHtYcltVvWVWSMzSfDb/xZjXvM/GbODayblnMLrZA4y+U+s9IP0rjT5ceMbbTS9Nx6e/sdLz8bt0rt/h9r/kDcK4cqW/CCiLZKxG/UE+pBB95paxHFsA95Va0M2fw3V7Q7xM6wQ30JrpOKCWsRbLQFvL4cnbxF8yfNS4+Apa4/9mKYi+95bxt5zJUKCJiD1+NaYvEQUuWRtKStd/hrX369jLK24qMEtfj4/jv3BN7kiy2ID5ALRQ5kBIGfTKRHpPy9ao8R5HV84w3lZWUQ7MVJiW11I0ZflXU7GENqyM37R0TUgRnKjf0Jig/C7+HxYN7BXQtz86EaT2uWjsf8Qg6b0sXi8m3elq/Tvt+u1qxzhi1rvdevXS9O90cnxPJWLQxlQkiRDbxqQJA1jpQ+9y/iV3tGIBkMpEExO+06Ht1iu08W4nZS04xSNaZRJygtEHS5bI/EAYPcdRFJuK5uwbAkP5suePDfL4o8rjb8F1JkdIneu3DlnNXNV+On91/n9nHljCL+lnOL1pkYq4KsDBB3BrXR/mnF4e7DWXLMrFASrAvajNbJJH4lk29dTlB1pfrVsyMr2srykBIGpTWsVKtYy0B4TWV4KlULYEayvYrypYHor2oipVS6AmHrKwGsp8mUSujWug8vvabAWScPN25eGGtt4jy0jW5poMuun+HekO5rpB3iBuddh611LDcPsGLa2zbFkrki5cOS4wFy5l10IX83dvSnmdVt/A4x5FHmLhtlkgYY23GNTD2HLXCzrILv5jtAOo7TTNxzg3DbQUX7LsDJAAv3dusLMb71V+7K1yyxDMyE3AXa42WZygZiROq+vlqniObzcxZwQUZTIdy0bLLKFjU9N+/auCfOU4xTddvdfb7+1m6xpRbaV9LXyS5j4FZOGw6hW8W/etKJdzALZ20JjRQaJXsBgkxaYYm4Ga3mVBdv667yG0ACnfvXmNxFvxbd65cUeCGygsoUEiC2vULI+JpW4Dxuzcxl/G3byJ/wrIdwGyDc5Trrp8zSjPLKLdvp+/bel8DlhhCSjrte3SW38jZewGDOJXCo13xYFxx4t+Bb7zmiSYEb61RwPLeES9i7yoypaARSty4pLhS9w5g0mSyDfdaAcsc0YfxcVib10JcuXIUNmnwlHkjQ/H2q/wD/ADDBLFtbpyAl2OS4czFs393XzeYn29apLMnxt9Lf8uv6X5Fxxtcteuv4V/2M1vlvDstu3eF13NuWm7eKzADTLQJk6ddaEYHkfACy1xsO1yXcoAzZimaFjzARAmT0NDLvP9h0uLcN0BiwXwwQwTYHNIhjqfSRVXifPlprWS3ZvDKsJ5gigxAzZHmB2peV4h6k3t+/zra/GvYH5SVr29v/AD/LFLm+5hDey4O0baICr5iSS8wepECOh6mgYFTy1st267VCtHNRqCVA1euJAqkac40DR5WV7FehJpKLEeRWE1ZGDJH6zWu/hyp71pLHOK6A0ipVO3ZPXarS2RG2gp48UmtgUgK8q3ftLEqaqRUTjxdDJV5UlU9q8rOhhTBWJuiLi2yMzB2IABVSy6nSSQAPUinXh+EwtyyrNxC6Ha2HuKMTZQi46h3UKVnViR6ZdTSHcOteKa7HGwofcVwvBjNOPvv5SV//AC7fmhFO+Q/mZhG/lPrQrhHLmCu20e/fZGaGc+PYgBvE/KVzZhkQkH/mjrNKzCoXBUPHoKHPEcpcPDWwuILKxTO/j4cBAZDtGXUKQgy7nxB8R/FeV8MqKbV8ZjdyEvdtMoXKzTCCT+EayB5l3mQsmzUctZ+W/UKLfF+FHD3MhuW7m5DW2zDRiNex0n41UipKgo/ydyvc4hiVsW5CjzXn6Ik6/wCY7Ad/QGqrigoX5gfp/OtaGCDMV9ScA+zvAWFE4OyW7vN5vi1zSfYAUC+0Xme/w0L4HD7ItnQXXAKz2yJEfE1hKTbsaV6Pn+ysnpV3C4TMTFHeJ87Y3GEo3hIGWCLdsLAkTBMsDoNZ61Uwtp1I/aSSNmJbTrvXRCSfoVVFPHYQ5apW8Ad6KYnGhry23hZIGYSV12JUAtHtJ9KvY7hb2gGYAo34LiMHtv8A9rrp8NCOoFbR8uT+5m7F98FA0qxgsGBM1bday3pWyilsiyPggdK1HC6VeSetTNsUwBf3YzG9WLuHgRGlGLWBhZNV8TbkR0pBYt4sgeUfOvGtBYohfwI80b1VbAMVB+lYyg9ui0yaMI/9VlaVQga15WXJ+xdBbllCcSAtlL5ysfDdgqsAMx1PWAY9+00dXAgW2J4Ypl2TMMQZRjdNsLG3lYBfiDtICzwrCi5dCNeWzOztoAZA6Ed/pRLDcBdpy43CrDNob7A+V4zfh2JGYH2OlW+xBt+ENlI/sgEwQGGIMTsGjMNJy9vrQJOI4dWfPgAQVRYNxxDLmzsDH5pXQaeWtq8LfQDH24zIv71xq0LKjqoJOumgn0FXjHC2tAE4izdBJUC3cZyIEyQQIHShJPv/ALAGY10Z2a2nhoT5UktlEf3jqf8AeqhTWrBrW4pyQzXastcYKgJJMADck6Cvp7kPlkcNwQVbee+wDXSIBZz0k7Ku3z71x/7KOGocScRd/dYZTcJO2aCR8oJ+Ap9+0D7Q2Sxbt4Qlb15A5YiGt2ztodnP0g1zz/VQNPo3c18adGK4ridjCf8ASthrtwD1VdfnSVx7mC5hbWe0337CXiEdrzJ4THcqtkAXFaFaWOxj4pdnBqz5rjEljLufMdTqddzuaYeKcGGIJDMbVlLU4YrDIVSBln+8ZJPWZmn5bSBgzhODwt3M2HulLjEKtm/MgHU5bqjKwmAJjbWNJIYnlvFWnXxLLRvnUqVA1EG5ORZ9TpINLXC+GB7q2xcQFmABeQNdBJE0wcQ4ViLbHDXS7OhgJmLDXbKNoIjapjyqi6os4ng+FF4XcFxEeOCDbt3HtW/CuSNfHnI8CYyjU7nefbHCsTgb91he8KwQGcYpQ9vEaZnhFJW4Z2I110O9Rvcu4S1cOFu2XuX0ys9y2zks2jugtiB4ceQfmJkyNBVfF4W3iBGGdjeLEWrD52NsBoKLJKqcqhsxidtCNee25WmVxqO12e4zGcOutKvdw4aIBXOgPWATny/EnaqXEMI1l4aCCMyMplWU7EHt9RFbOeOAYbDZEsvNyCWWDPfVjuYnSKrcHxCrgyL+YL4q+HmEiCHDZWHmTVdxIlDII0HVDPKL+p2jFxv0Nlgz/GjHCsFnbMR5R+tVl4O6sqjzK/mVxBBXvIJG3Y/WQGDGsLFoKBqRp39TXXzTWiHGgbxK6B5R03oRcImsxV2BJqkl4k1SFRbe2DNaAwGnapOx6GtSYdm1nSgdFf3FZRTw6ysWtlWB+DYhkvArZF7ysDbKlpBENoAdhPSrY5it5MpwtjR8w0ggeKbmSI2gsvseu1aOBR48+MtkhWyu4BWSMsGfRm+VM9riV1yLh4lhQyh1EqoI8wBMab5FIPak6TGBsXzFauK7HA2BnJ867hmnZiPxdR7T3oMWBMgR7mfrApxt8WYm6v37D5XchiyKJnDrDAA6AGF33U9opUxtpUvFFdHVQAGt/haRm+YmD7VUH6AjVkrYtiYrfatURsYTUR8KtjOp8o8pFeFqsa3mFy4BuySCE+IAHxNKnMXKuIW67PDsxLHLMmddFIkgdxIHeuzctXh4CW+ttQh91EH4SD8jRDFYNLilLiK6nowBHyNcSnTsnkz5v4fwe5eueHbUsevYDuT0FdT4FyPh8PbnEnxS+9vUoTGwT859T8qNcU5avLbYYPEZPzeHeXxVnfyuTnGvQlhpoBRDDYezhbfiXrmoHmuXDqT1+vQVUsjfQNgRfs/wN10uthfCKZcoVyJC/hzKNOgo7i0w1tncC34oQFj5c4QCBPULApK4xzxexVz7vgPICD+0OjGBJj+6I+PtQe5bGHwF5RdnEYn8bsfMF/DMT2LQCdZ3qJJpbBb7Zv4Jyv8AfbzY/W14jhlcM2YgEghVEZRI3nX40yYzght4q1et5fw5PMBoAZfYQJWdBGo965ZwTj9zB44MpuG1nANpSSChUKoC7EgFY7wO9OHN32m20KLZUtlYNcLCJA0KLro8GZIjyx1rOWJQSvt7N+cpPXQsc2KuKw7373le291CIILujwI6CBAIA+tL3AMDaOFa3iEZgbisMlxVe0zIWR4gghlIEN/dO1MPPGM8S2ty0/iIR4jXXHhgToQRuzSBsuh002oDw3FMl1Ve4WT90Vtrna4zIWzlJEgOqAbQEHxyh+r6ujTJVKvZjbwHAJYtSdAo67gbj4kn5mgfFcTnYux+HYdBVzi2MNuBcbJYKnM+Us4bQr5FMbkLIJ1PQa0tXbq3v3NzOoEsGUo4E5ZKyREkCQx3G1eljlHpHG9la45c/wBbVtWzFWEtRsK3WsMWAJ0X9a3sCpasFj6VbYZREVaAXbLtVfGMYGlKwKb3jPT6VlelfT6V7UMoC8N4h4NwXPDS5oRluCVMiNqt4jmBWDgYTDKWQpKW4KzMsOzQ0fAUGuGtdYyexMJca4p94cP4SWsq5AtsEKFBJAg9pNU1atQNe0RdAho4PDLPbenLljChrgYjRBn942HxYqK5vwbF5G9DpXU+TLyRckMWIzLlEqPDBeGPQMY/01eSVRsdWx75LDPfvvPkt5bK+rCTcP8Aqk/56cqXeWcG2HwttRBZpds3Ut/GMvyoxYxeYwVIPzFciQpdnvEcWLVp7jbKpP8AKuA8x8du4m5LuSAdB0HwrrX2gYgm14S/m1PtXKLvDT2roxJLYJGrlvCZ79sawTrHYAk/QVe4/iDa/fllRR+JfMraGBCyyT5tCMoncEwLnL9s2LguFZGoI7giDr3/AJVs5m4HaxQbLibiZh+B1WAfcRp9dPhRktsTFXlDFW8XxFD4beGBmusxjLCeGpBU6HPlMzPaCKcvtA5Xwz4W9iWTw7tgSHteUPEbpMTJ9/XWq/2a8pPYS67HzsUAKmQCuaSPQ5hp70p/aXzTiGZcJnyKgzOqSskiVDGZ0BmPWuWUnypmi/TdnQMB9nvDjbQqCyMAwHiOQZAMxm9F+QohxPkvD5JtqLbLqjiZUjUEGehiuHco4oW8XZcrIZnVlHVWtlYk+9GOc8c9m+y2r1xesKzLHrAO8/Hf0rOTXsa47ab5VR79pVtj4ThMqiVeBlU3BEso6qR1GgzR70ORkh8Rr/8Ay3R8c1uAfjHxii/L9rieOteJCX7IdbZ8ZxLOoVV1OoIGXWBMazTTwPlz7uC122EuXVVjbERbQElV667E6nZdTFbw20YP3F7CcHMZrm24H8634pO3+1MeNsz09qGX0UbxXZysVAYqY2qrfXaauYvHASBHpQ52kiqQESaytjhZ/wDdZUtgJlwVrra1QisZRsTR4oqYqxatyNP41bsYEsQI303rRY6RSQOVorsH2HXbZa9cuXFkKqBSeknMSDv0+ZrnuM4H4cBpYnUZdf1ozwDFX7IK2Lt+3LZiq+GQTsTDjfQfKonGT66KS0fSYVSNCI9DWsYeCDO2tIPJGNxVwXFv3bxIZSrN4YlSII8ojQwdp1pvvEorE3rkAdch/VaxpozapgnjNk3HZo02HsKCtwskkR9KrtzBjGa54N22yoJy3LaSdDqMoGgMfCnngGOtYm0txVAJAJHaR/PMPdTTU60U1SEv+yD2qN7hem1dH+6IelV8RwpCNKfmE2c+4sq4bAm4zZQoLHWJ80QD3PlA69q+f+I41711rtwyzGTJnYQBPoAB8K6v9uPHFVbeBQnNpcueiiQi/Ftf8o71ybBWC9xEH5mA/n9JrF92U5WqCFjDui4e8o1F1jrp+7yMNT8aK802Bnt3Wl3vKWJM6CcqHTTULPvPoSU5iw627RtroUxSZ9AMouWlPeTtNLwx2fDracEtZci3BOxBJB0gRGnufeomqRripyo679jeCRsJft3LalTcnaVbMgkQeoiPbLRvjGFCXMokhUVZJkmBpJOp0gT6UG+yyw33dXt3ZAdg6xE5vwmZ7GPhV/mviyrdcfmED6U8EvcrLH66QD4tiwk0pcQx7Gt2PxBdiSao3LJrsU0JYit1mpWxrVu1g2MwNt6KYfhgESJq1kQpRArIe361lM97Aa/iI0GgI7CsqeRNHKXFQqb1rqmQbbTVvVyKpg1NbhpqaCwtgcWoMvmjsNid9dqvXuJ22yauAu479NSPj86Xlfu1bLZBO/0n6Ch0ykx+5M4slrFhhechpt5SGiH1Xc7yNz2p7+0Xmbwba2UdVuXNfNOi99P6k+lIHJOFsNbfxGBbOAsgrGhade2Xel3jnGvveJe9cYAmAAJhVAgD23PuTWMmr2VxtjHa4heRS9u7anKQYKk+0MCKZvsg5lPjtZuH95DL7tMj28RbkD/q0s8vcAs3wIYsJgkGNt4HxFDeAWGGLezJVw1y2pGmVwC1s+wayP8AWKmXaCVNM+nxQHnTmFcHh2uHVoOUdSfaqH2d82jH2JeBet6XV+kx09fWr3NHL2HxS/ts4I/CUaCPgZX5is2mjKNJ7PlbimNu4vEtcuEm5cf8x2kwB6AV5wO8Uv22UAkEwD18pinnm3kG3Yul/vYVZkKRLj3YQJ+FKOGw1q1cnxFuDYaQPkf1/SqjB6slvehg5l8bEW3uNbFtrgTMgAljb2OaddPLt27UqcIusbsCJfQ5pgmc2sU38cv/ALDOjDyg6BfwvByn00Bg+1IZfzZtBrOmgHsOgpZo+g4SppnX/sTxvnu22YggaDcaNpqDHWPX4akuchOKuEdwP/EClH7LkK4kG1cVnZAmRc7MCYYsYXIAF01YayK6nZ4OpOd/MxMydhPpXPBNP7HXzV2INjglxzIEDuaJ2uBKsdTTliMPA0qg1iRWrlQc2ynhOCFllRqI09D/AF9ar8QwORsvbT+vjThwPHrbBzAQOvuaA8bx9tiYUA/Gd/eKTyJKyY8nKqF+7b127fpWV5iMSMx3/oVlZ+fH3NfKfscbeoipNUSa9CzjNyNW9HqkGr1bsVMshcaDuCOuw+MUfwmFW9+BkU9QwVR7gxSdh74MRM9qM2HIiFAOmpmf5fSuWeauzqjDl0NuD4CT+ISO6qCPntRA8vW2GiW9Ot0J+oM/SlV+N4ltGusf0qdm4SRmJb3JNYS8QvQ0WCXuErpOGaLTWB/9RYR77fSqeFNtb5xDw1xjJOZ99IIAG8qD86licIYzGB7/AMO9UGdQNDPf0+VZefKtF+TF9hfk7FHCcRt3Eu5rd5mS55SpUvJAIJM+bLr6dJrofMHMLgFbenr1rkC4wq6sNcrBh8DP8K6/awy37aumquoZfY6/Ou3w2Xn+rs4fFYuD0c54zhWuyWJ1pT4hy841X66V3mzydmEsSo9pMe1WLfL1pNQgkdTqfma6pygzkimfPT8MxNu0fERja0zSrGFmZBjaQCR6Vb4dyolwBheJB6QFI+Os/Ku/HBJBkCNjPauQ8z4hcBiGticp8yQN1P8AIyPhWUHBumVJNdBPD8JXB2kv2A022BJn57AV03h+JF22lxYh1DiNRBE9hXLeG8YXEYd1Eaj4/GjP2UcTa7hHsE+fD3WSOuRpZPqLg+FPPqNhj7odsVcA3oJicdvlFGcfhsiy3XQUDuWq8jxGdxdI78ME+wffxNw6TVXwe9E3t1Wub1xPI5dnfBJdA28mprK3YgeY/D9K9rVPQm9nFmaozUstYFr2HI8jiyNYomtgSiXCuFNcMgVEppK2aY8MpOigqlCDTLhiCN+nf5/z+IqnzDhsrgLqsACNttveZqnh7N6IVHK/9pj9Kwf1xs7IrypUEMTjAk9+g/3qWB4yoPmU/Ofr/tVJuH3NWYAACdSP0qOBwyuxBYLodTqJ6VPGFF3NyGTC4s32Y3WAQDdZEDsCdY7nSqt25hgYtBxGoYmR7EHUj5Ghj3coyD4+tSsJNRVF0XsMJ3rsv2SY4NYeyfxW2lT1yt/Jg3+quSYTDmQRT1yhxJrd+3lCjMwUgLBKsYjf2+VLHk4zTM/EY+WNnX7o0oViTRHEN5aB469E/OvSk6R5MVbKeIG8mud/afwgXsKbqj9pY84P+D84+Xm/y+tO2KxXrQ25eBkHUbEHqOtebkzcZpo6o43JUcP5Zxxt3InRtDTz9luJyY7F280F1W4vY5X/AF/an5GkLmHAfdsVdtJsjSn/AGkBl+hA+FNv2bcRwwxb+IwS5dVBaLAwbmqsubpmDbHQkD0r0Zy54HXwcqVT2dj4lxJrgy9BQe84FacXjAOtAOI8XRBLuAPU14D5ZHbPWxYaQWu4gd6rNeFJHEedAJFpM3+JtB8t/wBKWsfzBfu/iuEDsvlH0/jXTDwk39i5+Ixw+50fF8QQORnUbfmHava5MLp71ldS8Lrs5n4pX0bWUCoeKB61quTOtepaJ6V0Uq2YOcm6ignwXDtfurbVdWMV0uxy0cNlLsoBB766bbddqGfZpyuzt4zNlyFSNATTbz7GRdYI/KTrr/6rzc+TlOl0d+FuLUX36nL+LcRTOQGjXsarXHbICMQ2XtLj+hNeJwR7lzY6nprTI3Bm8HwOx2Ig/wBda2bjGqLUZzbtfgV8ZjVyi3AkalgSSe0z13r3hwZtFB+FFLfKDLcGaImnJuXfCQMFgAQflIqZ5YJUhwxyu5aOWYu8VcyPnWtcc/QxRfjnCHDkxvrUeA8KPiDOCB61upw4Wc8oZfMr0KNvi15dM3zGtGuR8ZcOPwxLEk3rY3/xgbVvucrlr2VdjtFF+ROXivEbHZXzT/2gt/AUQyQbVdiyYpqLt6O9454WlLiV8660w8auwkUo31ZpPQbntp/X0rqnKKX1Hn44t9AjG4xh0PwoY2NIOoI+FGrWDLuANJ7/ANf1Fb+I8JCaASx/revNzyx/tPRw2tSOa89YEXQl62CbkhGABllM5THcHT4+lLFzCXsFdsvdtidLiq0EMAdQe38JkV2y3hbSOqtuYUjuTsNu4ArkfPikYt87M7MqtLbKI/CPjP1rr8HLlj/Bx+LilkteoR5ixuLNxspcIYZQCs5WAaCV33iRpSriS8+fMT3aZ+tO+Fw7HD2HVGINlPoMvTpKz7UH4jaPUe9dUcEEtC8yUlsV2NRojfUHpVRkFDhRm0aqytgQd6ylQqY24HlG44B7/wBfzota5fVWVFUlpj3NdSwWEC21BAkDUDaetK/FbtwXxFsLlMho1I31I0rxXmnLs9rE4NtRQz8Awfh21XLlOXX396WeecI5vKcvlIAnue1N2GxJnz5Qd8o1gd5rMWUYqzR5TMnbaP69qxjNKN+tmEZuOS2hU4DwcrbuFlho8pPQ9/hWzhXBSG8VzKg9eumpmieK5iw+UjMdt1/n3oXd5swgt5GzQBtH+9P638m/Ob9KL97haOoM5dWI2nbbTppRPA4LKih9T1G4k+n0pMbnywq5EWRGmaN6mnN114CACTA670njm1sTU5asJcS4KGvHLb8oQx6t0GnWf0rzh/LQ8JhchHOq7TvtH8a3cex1xRbRW84gkjvB+mtD8VYvk2/FuQz9J6HqY0HSmuVdmkXJpboZrHC7aJKSzJMHczHX5VHgvCRaZmIl50O0A+vf09qMcNs+HZCAAMB8Ca1YK8XJ6akMDvIP8v1qpLhXuzieSTUl6FbGcaTYqzDVSYggjcQdeorOGr4itCAIRAnc+/cVmJsoS0nzFoOxiQBG3YDerLY23YtwSNNAOvw+lEJub+tiaSjUVsr4HChFhgAwMmP4TQjjnNFu0JVlaZkEHT+u1DeYeOO8qpgdl/iaTMQjMd66sPgpS29Il5Ip29sLvxlbjrca5+ddNuvY9Ipf+0/hrnEI6gmUYE9BlM6n/Map8SRpgE6bRTJ9omMBwq5RLXVDMxOiKYfQblmmPYmvVwwUVVHJndtMq8uYmcBZgklQ6eWZAFxiJHaD17Gh3E7hOp1Hp0+FUOA8aFmxbBBAJuISDuJVp9/OR/l9auY9AIa2+cESOnzpxV9Dg9C9it9KomiWMEk9DQx6TZUieasqANZU2Sd8xHOWEAOa8ABsRJ/Sk/jXPWH1NsM7E7nQDToP/dcuNw96jNcK8FG7k7N14xR/QhyxnP8AiGiDAGgoTiuacTc/FdaO1A6yt4+Hxx6RnLxeR+pcbiFw/mPzrSb7Hc1qAqxhcIzmFBNW1GJMZZJutmWnM11bkXhSXbLMT5wPLPTTf2pWwfJN0EZ4U9m0n47fOnzgWD8FFIJKs2Ty/mMHb/CASfXTavP8TljJfSejhhKEXb2HeC8IVrZLmXYCdfMARpr8jRDH8PVlU/mXY/7VPDkWwZ3Jn4QAP0AofgLrtcOhVJYwfUDSfQ9K45P6ddk3KUnK+g9nIELvGk7CqNzEC3mJInrA1rLNwkBttYI+lc/5t4zcDMGLACYHb5UqlkkgxYru/kIcW5vyOWWFHUGDPSSO/wDKkvjHOLOxOYk9O3ypW4lj2djJqgWr0cXhYrbFl8RGOooeOHcwi5o+jfrRlbgPauXpcI2pi4Tx2PK5iTvXpY2umcMt7QbxFiTpTnwnhFnE4Gb6MxVSgy5xoqwohNTp1j60G4LaW4yg6hjFdDtYVLVhkBCqZ3Pca6k1vJV0YydnCuPcJOe2i2zaQ+IqgAFSbSgOytPmZsuoYggjUxAFzEctFdFZmCIJKqoAZV88zcXzSfqKP8xcYwzea5dtuyvntsjDMJQW2Oh1De5Hl9qWb3HrI/ds4M5pkDzTMnQ6yBrvWEoUXCTBHE8JdtElx5ehJXMR3yhjp6gkUPYg9aa8TxzDXrCW7mfTVlzNGYE6zHX02mKU7qqGMbdNT/U0mVbPPDrK3hl9aylQUihWCsispGJJVq7heF3HOimqStG1EsLxq4mxrOfP9p0YfK/eOXBORFyZ7za5SQFBMaaT6/yph5a4XhEUJqbhOp6gAz8tBSHg+criESMw6iY+vejnDufbQbM1mD3B19iY1rz8mPM+z0Y5MVVFjtxbCIwM3MqDZR/6+leYVcipcXNkVDAacxuEd/aPTSgGH55w2WIIksWJILS3Yn+tBU05wwgmQz/9zGCfUbd6w8ufVGiev9Dzg8t39ruGSNfc/wBfCpYPBMrsS0I2wO89T8hSQOcbblCrZSpJIBIBECBFMmI5otALDpAE6tqwjp2M96nhXZlKE/2+oSx90W7ZDNvMEeu3xrn3OFrxWlT+UA+p60QxvNNh82ZhE6Cf170p8Y5gt6hKvHCXK6NoRUI3Jibi7UMR61XNWMRezGartXrRutnk5a5OjwVhr0V5VoyGbk7mHwLqC68WwZnUxHT2NdI4hxZcUkW7oyNoCAGHrOxOnT1riFbLN9kMoxU+hitY5K0yWhvxvJF8sSLwbXSQR9JNUMXyfdQaup16A/x+Ne8P5zxCHznOPkfX0miw5qt3QAdO4O/10Jq0oSBCw/BHH5h9f4Vav8s3B+F7b6TKkx/5AUUxeIVjIqfD8UCcrN6LA37zHv8ASmscbKFhuHXQYK/UVlON6wsnf5VlLykFiBNZXlblwlwkLkaSYAII17a1gRZqrKujhGI/5L9ttjMa9v5Qdqhc4ddUBmtkAxBMR5vw9esH5UUFlWsq8eEX/wDlP8p6Mf0R/wDSa8PCb/8Aym1JGmuo0O3qCPgaKYymGr3OanfsMhh1KmJg9tv1ke4irJ4PiNf2L6b6f1/vSork16lUXSOte/eD3pi4UyJaQ3MM9wBSxPhIQAS5DZpkypTQ9FB6ipYjH2h5RhitzKWKi1bOpU6k6mBIOwOnwp8EHmy9xZ8U96J8GwVu/nVnZXCFlJKhJkAAz7+nvRN8fZacuDzKhDOTaUMozBwNDoPDS4Nd8o6E1HHMjxGFdVYnKwsqCc6sEgA6x42GI119NKaihObZrx/BLKI7rdLQpZPPbIfUwsDqqBWJnXYQYrU/C7Hh23Dt5ntKwLoYDorN+XQjMddfw0SQIDAwjPmfyg2Len7Us0HNr+zOUdPID1qgeFs1wt93fKVIzBUGps5QwtgwPOC+h2M9KdE2zzGcGsrbZ0uliHRQuYSqsqEk+SCQWPYbazpWxuCYcEftWKsxVSHURF5bRMlNYDZtht8a84jibHioFw7KyXS7qbSCbba5Tb12QLv3Y6VbweMwxKj7qxOUqy+ChzNvIJ2hQ+n+L0p0hbKuJ4DZSwLhuMXCg3EBWVOYBhGU6jXQn3I2IZMA7a21lSSFlkB3MSJ30ptuX7AgtgWRAFJP3dCZZyAPQfhGupnpoGGcxWrblFs4V0YF1MWwmYnw8h0BnzXB5ezJrrQ0gTJYXly01lbhunMVUlAUnNqSo3MnyAabk71stcrWj/xj+TyyubUKWXQGWGb8oYe9Sw+NwpPh/d8z5iQBYQlQFHlIBlssMSZ1g+9bruIwyF/FwbqkLlZrKqe8mAAPMB7hj0IAehbAt/hrob2UvltW0uCepbLp00ANzWPyUb/sa2FdluscmaD5YZlzSuoHmlBtP7xd9zDB2LIW3nw1zMoXMDhpGlrK0+XzHO6t5p/Eu2gG3LYCmcDcXfQ4fYvkI1KzAY3FEEaFY1IIFofJg3+051kbDqaygCNoKyp8xm+jTVvD8RuIQQZIbMCxJMxHeqteVJiHF5pvCTlt+ZgzaPqQAJ/F2Cj4TvrVPEcXd8ucIQvhwCCQRbzZQZOo87T3ofXtO2KgwnMt4TAQSIOjn8xbqx18zCeoNZ/8juzOW3JMnRjMObgmWOgckj4UHryi2Og47XnU6WgLiKp0eYHmU6zr5tOgAAEACjWGxt+4FYtbBuD+452uqgBJuDrcDaiIMbQKTReYCMzAe5/SmjD4YeFZXJ5wxZ2N9IykkgKFuAr+UmNytNMKLeCt3vDRFayRcQKJQy4Fo5QWDyD5cvk0kz1qg3C7l0i+by22Y5JGYaglAAS06xsO40iSCK4S1GykzsL7+4/4u+p+tLONuqruhLhVINsI8qpiWjMW3JB0PemwQw4LDXvEu2w1oi5CvmUnN+O0dQ8iBbc6H82oEHLmPu3xYLlrZVfDcAJB3FxRKOQABlHcj0hjQwN6zFwNeYsNbWa6wBOVSAWBEDN+m4jW44wUgeMSjR4k3HM6GCRnGsheh22HUEWicSoLk2wLa55W250I8dhq4B/dopO83CJmYj4GIV0XPaUiQrAEmVf7uPMz6/8A7BMTIg6TMh8Dcss7Z2ATJbUDxH3ZQbu7iVkFSPVdoJohbfChVi+0nKXHiuAJNsOPxDWRcbc/iG8aFhRVx/CLty6LviKDcQuCgIgIiaDKxlsrKYBM6RMiruC4LiAQVvK5V2Azlyc0G2R5X6w0azPbUVocYSSfG83lP724QHK/tIMgsoy24MzM/iyhSM4Nj7fnOJuXCYXIc10+YBss5WB0Yr8M0a0aHsP45r5tXD4lkZIYwram2c+nnKzNsiDGg6Emq/CMdfumClq2F8IqGRxr4ikEEuCdVWZJnL7mtV18MwM3CQZgNefYiBIz75SAfiKwnCn/AImvQm82wJj81AqLGD5fxCPnTwlYM3mIefM1y2dSdQBkP+dZJg1sfD4nFpdBFkL4nhNAu+Vle2oIBY92MRtabSaV+H48Asbty7BGmRzJaV38w/KpHy7UZs3cMyHKb8ENnm7AYhYnKXkyCOnehNBTDSrirQfL92BIN5kh9WjPcli2k+UEkwPL+HQnTefF4VZIseVN4uarbzQImO5n/GO1DcVctkQrX4ZhnU3ZDJMOp8+rZQwqLsgDH7xilmTAuAg+Xrrr/d6kj5U7ChaXasr1NqysjU1GsrKymZGVlZWUAZWVlZQBlelj3PzrysoAkLrf3j8zVjBYzw5lc09yR+ms/GqtZQAVPGP+kv8Arudo7+s+9S/trb9kun+N+4Ouuu31NCBXtO2Ug1/b2utld5/E4/Q7VA8b/wCkP9dz9ZmhNYaOTGwjc4tJB8MCDMZmIO/ee+sdq9XiwBnwl/1OekdT8fehdZRbJsKni4n90vtmYD6a9O/WrvAuJWlzm4FkkRnk6BQN++h1P1per0UWMa7vEbMmGtanSQ+g07N79BUEx9kfmtHUnXxD10Gjjv67Ur15RYDQMZZj8dvrrDd5/vfCtmHv2WDgG0xILfgaQAuv5/SdO+oO1KdePRYG62NBWVibVlI0o//Z"));
        simlarTVShowList.add(new SimilarTvShows("Fight Club", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Wild", "data:image/jpeg;base64,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"));
        simlarTVShowList.add(new SimilarTvShows("Fast&Furious", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxITEhUTExIWFRUXGRgYGBgYGBkZGBgYGBcWFh8XFxgYHSggHR0lHRgXITEhJSkrLi4uFx8zODMtNygtMCsBCgoKDg0OGxAQGy0mICUtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAQsAvQMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAEAAIDBQYBBwj/xABAEAACAQIEAwYDBgQFBAIDAAABAhEAAwQSITEFQVEGEyJhcYEHMpEUI0KhwdEVUmKxM4KS4fBDU3LxJKIWZLL/xAAZAQADAQEBAAAAAAAAAAAAAAAAAQIDBAX/xAArEQACAgICAgEBBwUAAAAAAAAAAQIRAyESMQRBUfAFEyJxobHRFGGBkcH/2gAMAwEAAhEDEQA/ANCmGAqZEqXLXQle1SPCsSLT8lPRKl7ulZVA+SlloxcI51CmKcuCc8qXND4MByUslWD8PYdD6VNheGZjvSeRJWNY23VFVkroWr63wEmZaDyoG7w91JGUmKlZYvpjeKUdtE3CUQmCfad60lgACBWUsI6mQNfSr7h+IdvmEVzeRFvZ1ePNLQRfwmY6kgeVPwuHyDeammlNcvJ1R1qEU7O1DingUmvgGKG4ld8JinCLbQpzXFme4jdzEwar8lGOskmmZK9SOlR5MrbsF7ulkovJTGSq5E0DFaS26I7uuxTsVA5t0w2qMKURZwpjapckhqNgfdU4W6NWxTlw5JqXItRBFWnhaN+zAc5rq2mOw+gqeaK4sdh7LDnVmiAAGNaD4akt6VQ/ELsfieIFVTEpasojZUKuxa6wy52KusZVPh3gkmDOnLmnujrwwtWar7PO+1TYTDZQdta8+xXYjH3LeLtDFWMuIuW3nJdzKLWQLbLBx4cqQQAD4jrV12v7OYjGWMPbN+3ba2e8uQjd29xbbKqhC3+HnaSpJ0FYuTejZQinZrFBG9RBpkRXlmJ+HLsbad+pS2llVLNda5aZLhu3GsmYBuMSJPyjQbUVf+HOOZ799sbbN3EW7yMclwBe+KiF8ZlVtrlGixPOltdj0+mbq7KMfWjbV/MJ2rynGfDO+bbWxetjNcFyDnNpCqBFGS5nD/iYzGpG0VoeJdmb4+wLYu2lXCGWVkbLcbIEBCowyxLkCYkjpWr36Ml+F9mwxZIEzSwN8nQg15lh+weLJxB+2Ir3zcm4EJZBdfM+WdZyyo8QHiJ3ipB8LsSbQtfabRUNiWAIugA31VFIAeZthZALHU84qZOlTRUVbtM9BvFySQDpQGIvE71W8c7GC4mAtM2e3hQA5YtnuqtsLlMGIZgrH0FZ7s12JvYfEjEXbqOwW6GZA4e6bj5g1zMYhV0Citscm/Rhlil7NYwrmSsbb+HjnRL2R2xLXiyzIQSbVlcwIyq0EyDNSr8KsT3TI2IRibdtJJcTFx7zk6Gc1xhoRELFXLNx7REcPLp/oa3LXe68qn7K9kxhcLbssys6yWZEFtWYkkkKNByHtVumAE+VL+ojQ340kyhOGbpROD4czeVaMWh0pwEVm/KdaRrHxFe2A2eFWxEiSKNFsdBTqVc7k32zqjCMekVTWABqY8qWGsoD50YyBhTEtRpM1rz0c/DY5LK7xrUGMuQNNa7fkUL3sU4RvYpyrQ6xoAeu/rRwxCgDWq1rmkUwyd6tw5dkRycegy5j+goe7iSR0qKKQFWoRRDnJhHDrWuY8qs+9qrt3TECrCxh9iTWGVbtm+F6qI25B5bUKqO5jYdatIrtQp0avFfbBLOAVeZJqbapajyyalyb7K4paQmSd6iuYMGiaVJSa6G4J9kViwFEAVLSpUN2NJLSFSpUqQxUqVKgBUqVKgCqvYiaiVzOlNtipltdK7NJHn22xJc601wJ0pzpFMNJA2/Y01x2mukVyKskbFSpZ61y2NascPhuZ+lROdF44cmcsWBG1FUqVcrdnbGKiKlSrjGkUdpU1TXGcCihWPpUKcTOwqa001Ti0SppvRJSpUqksVKms4FMW8KdMTkkS0q4Gpr3QKKC0PpTQz4rpQz4o1SgyHlSILIoq3cjlUKCnitns5U6HNfPOonMmaflrhFCpA232RGlFOy09EqrJqxWbJPlRauRua4gI32qO6wPKsm+TN0uK0T9+KF4rxmzhrTXrz5LajU6nfQAAakk8hTBVBxu+UutduW2u2bdlYtKucm67t4gp0kBInlm86zy1CNmmGTnLiR8G+KfDMTd7lbrI5ML3iFQx8m1A/zEVqbt6a+dfiVcS5duBLJtvb0bwgHNAcwV8iBX0Lg7Uosn8I166CpwTUlbL8mDg6Q9r5qIk1IyRSy10Kjkdvs7YWiFuAUPXDUtWXGXFBBxPlUTX2oXG4nu0LZcx10mNuZNRcL4gt5AwAHvPn+tJcbop83Hl6CjNS2jFMpGrezJOh925UQrsV0LR0DdjWFC3t6Oy1DctVLZaRwV0GlFKtDEdNKK6op1SXQ0CnIYrhFIUAidWioW1rqEHYg+ldy1Kot30Rmqni3FrNp+6a4Lb3EZpPJEIBbfT5t6E432ww1glATcccl2B6FtvpO1eW9ruP3nv2MYoBNosjov8jchzIIP11rPM+UWl2a4Fxmm+im4rirDY1kfE/dXLy57syVUkKXmOQk7RpX0XathVVV+VQAPQCB+VfLPa17V28btswrCSIAjYbDmf0r03sH8VLYsJZxmYMvhW4AWzKAMubnPzD2XrWeBJRs28puTpej1uuqKF4XxOxiU7yxdS6n8yEETvB6HyNV/antLawNtWuAszkqiDdiBJJPJQNz5jQk10NpKzjUW3RexXIry7g/xht3MTas3EAFx8hYBgULGFJBJESQDrpM+Vab4gdprmDtqLQGd58TCVQCNTrqeUQfaiNy6KlHj2FdrHyjW5lDIUAJgBidD6nX/AE1SfC5lK3gqgd2xSZ8UaMM45EyTXneK41evO3eYsszwxtXUU2/CdIYRA5bBdYJGk3vZztvcwBWxfsqLGbXKCLiFoA3aCJj671Kwy5OXx9dG7yxWNQXv67PYopRQH8bsFA/erlYSNeUTWS7b9qMSllrmCuWkCAZmcSxJJ8KKwjYbwZLDprTfFWzGMHJ0jUcX45bw7qrzrlk9FYkZvQEa+3WrDBYkXLaONM6ho9RMV5V/+QvjsNauMVZwrqzqIVoJiV5GVPlzFWuD43dXD2grr4ZAESYAg+3iqU7LePR6NmFRXL6jcisDiOI3hGe4ZnbxQd9BHpQ9rFCPFdPODMc9iCvLb2qqRKiz0KkKdlpCtLOehy04U0VQcS7WWrOIWyy6SA7kgBSVzaDc6anaKzlJR7NYRlLSRooqh7Y8Pe/ZSyrsivdQXSpIY2RJZVIG5gfnV+ik1T9ouM2bFrM1xScwXQgwQwBmOnPpr0qMsqgzTFG5o807N4MYXjNi3h8RdNu73gdXnxL3TusgqDMqCJ/XXb/Eri7YfCHI2V3kA+QEn2nKPesHa7Vrh+LPi8QjNZOWzmBUlWdR40UfMAqsDsfEfQmfE/tBaxVoCx47YthhcgjViGygEA6ALPmfKssL/Bs38iNT0YQcTc92wBYsDG2501oDvLhz+IZmkagwdx13gmDTOE3c1oDmpYex1/Wk1wq7AjnMj/k1oYMD4m7XGUk+IzK/ynMdCOX7QajCQdNxR2PAJF1NSBr5jr61W95J/vTKW0erfBS8LP2y87GG7tQOpXOxMeWdfrWw45jbN+/giSEuqbl2zn2kKUhgOTan/JXjPZHi5XE21GUZ2C6/K4kaHow3B9vXUX+Lg49MXdT7gPcs2idAhRcgLDkCO813BKnWIrLM21xRrhjUuTF2lU3sfa7xLRFu5be4EnxQBccSYMQCNddKl+Kd+/iUFxV0cDIJUHKrSRv1rG9qcW1259otsygmVA0Knc5jOhidB58qJXtm72LVu/bD5DAuDQhT+IqBqf2owSljx/hW2XmjHJk30ijw9x1xAF5STlIgnroDK6jUDbpHOt5wviC3VshgMy+CY1lUBGhmVIU76z71iOLy183ACUVQpcTAJLQfrVhw/iHcuv3nzlUbqs5oaAAAczA/Xea3xzfbOfJFdI33C8VIMqCQxy6H5CAwOuh1J+hFE8SZ2GXD2ydYBU6oSCmY8iMrvp6EbUDwQw91JMplMgkaNmiAOhBHPT3rU4EggtI/l0HTX33pZqpo08WLlNUYLsVYZHv2C6sLTjUFsnLRWOhO59zyo7idjKEtl8gZwpYSuUaMdRtt9Y96m7h8Th7tyzaYxcZ2EyQpbw5hG2kactNq11y8HtsiOAFy94jaKCdVmdcvQ8ojasotvXr5/k6MmNRS+fj+P8A9nS2gIZkCz80kA6HxGSdY01iY5VwsYGVQBtB8RkATsB5Gprlpsga4uTLlTQ7gCQTMTzHtPPXngInLdJ1kqrQa0cXF8e6Oa099HpWJxMDQiSYEx+vkDQbPZHzXFJ65pM78vOvOrnEzEZ9ee51nfUgDc8uVUHGOM3LaLiluOR3jqyMRl8DKsAKNGiG11MmtnNROaOJs9jscYtg5MxYciBr6Gd/9q8+7S8YsXcYyYczczKGk/K4AllgGQB83SDQx4oHtaEhG5gjSZmB7NWCs8RRHJW0CJBJnxLpuoPUab86yzJSVG+C4Oz2TiXa03bSmw+VCocsuhZek8pE9NYrz/it+8Lb5nBRcxCQIMsTBIAZhCiZJ+byrgxyrg7zAgq+bJEAQygmdRuxY+9DY6+z2sxYwXsxtBlZPLbbT161lLchrSKTiGIkW5g6Hu+uUmSxPUSB/kovtMLwEd87DKPmKqAI+UKBP1JrL4i5Bga5RlGvKSf7sa2XFrIu2luIsBkzoJBgNzKjnp+PppNUlQSbbtmU4XfK5gNJg0fiHJhuoI9xp/eovsP3RuDUquYwN2LLIPQKs7eXWoLd1mVgNvm3Gh2JP5U7JavaEzNprtQxGpjrR1vBuRqwnfSTtrr00qLF4YJu4M69Py1p2UkNxeGfC4kBhDW2Vx57MCD0P71u24iuKw57xdHBz93DC3cJDK0AwpLa5TElmHI15ziswbKxJKeHUzEHYGdpnbTWrPhWKa3ausl3KxiUJIzCRDLyLAzpvr61LSZV0cxt6FKNGYSIG3ihS3n4diegqvs2XuEhFZsqs5A5Ku7R0E13EXy+rGT1o3s9jblm93iKWhWDKNymkkSDtAPtVVQHeHcbKI1tjKmCPIDMSOpmevWiP4pZGXvVLI6klVJgEgchGo02jnQXEwhxDNbEI5DqGjQNuDP8AVmHtVnwLDubQb7PabKSN2V4IjWFM6H+xq4RcnRnNpKzT8H4mWi8AO7CHvv5zbEAMZ5hiSBzrYcGuW8lxrV3vRlBnIVX5SRBJJO/QV53gb6uLqZSDGRgwDTIJCllJBiT031FGcON3DoUS9IBJyaGJC5eqsPm36inlw+19UV42dQmm/X/TUXLZa8txULKV1AgGCNp6/XnpoKq+0dwBMtvLkzQ7BvndRoo1nKokes1l+KcZxdxB3ZKW9JI00G6mBETO2869K0eD4Oceoa1LyA8W3trkYjmrMMvMaiKjx8Um/Wt/Br5nkxbtX8fJZcAvZysXYY6kIROigSFM7L/7rSLw+2dSLrnmWu5fyArM8O4Qthg7XraFZE3b4EmCPCEtGdzoCfWpsbxmwSBbxNu4y/P4bsCdsrORM67DlW8oQ76+v7HGskvzKxuo/wCeVUnHUGHN1MgcXRmUnUBkbUwf6XP0ir9iNpnUxoYMevSfyoPidj7oZyDFwsdfwsQf9MsPyrCe6N8fsoeCcULLcsADJBCEAxmC6idzv+XnVRh1Bzj+uD7AH22qw4NaKu9hEzMjzmYkZXb7sbAAhtIH9M86ixmFBcq2bad4UnMQSFG3LmakYXwzjVq1YFoWe+ck+HdYLExPKZ3GtVV7E3Ul83d65UtKzQADtoYAGWOehqz4AUF0ggxDbCY6H8jqNdaqeI4DNdMCAZf1k6mp96HaS2AW3XPLKCJ2EiBPKCK9E7KWLWPf7OjtbsWlLvH+IVzQtpCdR0LdAoG+vmqmRPnVz2T4hds3y1lSzMpXKDBIJBgHyIB/y0sibVrsuDVpMP7WYZbIu2bfyreIILliFgFSCdSGI+qjrVHhLpggGFAJO2p5cqu+0uGbvJYCSokc41ifSqPLl39h+poxp8VYszXNpKgq64C5cxJgTrptBofEW4ERqQNvX/akq6ipMS2q77Eabnb960MSfhfALl8qBCKSRmPUQcqqNWOuwj1ozD8CYK1yS1rK6l0jQyAAwOoJbKsa6kCZiiuFYh7i5nAVfD3dsahFUQDqdzlBJOpInmK0vAGHdv30myxYM6Oe9Bz5pII1MjXUTrrXO5y50j0JeNGOCOVvu/8Aaf7HnYwkORAImNd9NfYwPzqPhGJ7u9bfMQAdSADoQQdDod9q1nE+z1yw7PmS5auTctXkPhdCVEQflI0BU6zFYm2dBXR2cSNJ9lW/cRrZDFQukBQ3jmHPKS0bac9qI4ZxHNcuPlAhcpWSC2WQDIGw21FDYBM1pmUwzILZAjUKAu0b/KZ3JJoDhF26HvOHJYq8yAQ+YEzJ28WunMjzrTEpRpkZmp2GcD4wzf4lxMxI0IgnbnIH61YYji1m1efDlCCwUZzEAtDaREDWDvtVH2Yx4FwK7AAjKBlAE6bkc/Wu8UwmbEXgTJOUgTyZeXoTMVpLI/u00zJY1950WnATke9hXmNXWeh0P6fnQkxhRbRsly291GP8wzBlge516e1Drj1VsNiWk+HI4G5y5ln+1d4hxBUuNcW2rpcClS2usbxy00jyrCzSmWnEe0n2mzbtG3EQT4jqYjbQ9Z1P9hVBfuZTCKF66fTeD9etCnibxCwvoB+tF4DAYm6CyO3nGb8yKdqqSBJ3s9WvPbuMCJ0JYyB4pBBBOadZ/KqrtiBbs59AZkCZAEARH7/pRQVyh7vRuRieesDrEx51lOJKXtXAXLMxC+Lcanf9qeT4DHY3B8RuD7K2XJmdSSY1jMA5geQGvTzqTjaqzG5adSmaPCNIMzvrHTTlpUvELyhETVu7y92pIIChR4RB6r7z5VVi6pDWxuNfYGP1FZI1YreMNjKywWOaZ56LAIHQE0ZisK7t3iwPBlhTGmp1zBup2iqXia5Rbb+oj8krScP1cJOgAJ8j836iiRPozacMbMyR8oBP5xRvAlyZmHz5oBnYRy+pq4wrAjENA0ePYKP96pbOICr6sT67D9D+dZ5rlBo7Ps6UY+TGU+lf7B2Kw7XMzA+JVZvUKCYIqlS5Ekp/z3qzwmLlomJVx6yjCKr7moJ5THvuKnDcVTNPtKWPJm5xXff5g5xgn5dRXL7ZwPDA3mdfbTyptlRDNMmRI9SQNfXf1FSMwA8RE9Odatnn8S2Z0tqNMhIUwD1VYAG8xGvWam4dx+4guWzZUq/ijNH6Gstfx7G4XAAk6DeBtH0o7huINx5IjSP7/vUpb2bOS4Uiwbj7AEBIU7jOYnqRl3qltoY0A96l4gDmgDU8vSiFwbi2XYBARpmME+i7/lWlpGSt9BCY1FtkwQFZdtyWTKwj1BO/SosVxhSxuWww1thlaJygsxAjqYmosRIByga5DyENrrrUPEsWbllC0FgcrHSTlmJ/Kto5LxqvrbM5Y6m7LZMFaulsxKGWKOIBKkkieuhHn59KjjRdb0ZpZUVSw56b/SKKwl2y1pUusVaIDDTQNoD7AEH1oxMMLtwuSGkiSCDMKBPhJjaqyJOOiINqWymRCbBBBEPp/mGv/wDP50SnBLxsFsug8QPlz8+h+tXXHsKlvDxKh2K5RpmaTGnTQnWrXs1jLX2O2LlxAwDLBYA5QxA0J6RWKiro1cnVmc4BwVbiB8smYafwkeXpH1re8KwpRIA9tKy3Z66mGxN23nUo6hl1kaMw18yNa1C8dtdauFETuwnCmKoeO4YXbwtqwRmBckDWVEAnruo9KszeE6VW38eiYnxGAUynTbUEEnodvaql0RHsyV3h+KS8veSQDuNiPpQ2BZu9Z8jZWXcj01n2r1S2qsjAAvmBAIEiSOoqmU4u3g3wr4d3HdsiPbk7zE6E9OQ0G1ZuFM0jNsyeOYPhsvdmFIY3QScskrkcTGukGJorg2My3leJW8Cs6zmVVnf6ewqy4NwO82Gvo+Hu5mAyhlYAxLDkIObn6VTYThuItOtu+rYdSWZHfQBog+IjQEaTtMVLiXZccOuZEukgeJjvAGw3n1rL8Yu2wRlfNuPCdOR67TNaTh3CMUQVfD3MoFxwxtlpYKICyCIMe871S8T4BjRaDvhroVM7OchAUEjVpHQVLiEHWym4e5763E6uo89SBH50ZigUuOvKYP6Ef3qLgeCvXbvd2bD3rh1CqpLAAjxaDTlryra4v4f8RLkDCEifnJSSJnUF5nltU7TN0otW5foYiw+hU6azPWORHqZFSvhWYGASd4A/MmtBjexnErUscE2RAWLgIRAXVmhiABvXbfZDijBSME0NBBgRBGh0baNaWxqOP3L9DHfZ3/lNWfAQVurm0BP9p/2q5XsZxcnTAXP9I8v6vL8zVXjsFicPiFt4i13VwCchgEBtjud/0pq7CaxcXxbsH4uIc+TH9afhbf3bNzAqDHks2g1+tEYG81tGGWZGoIPLn5Voc/ol4gFW3JEk5Ty0BVuoMcqrSJC9InXrtP5D2riOzyDJmABqToDoB5CjjYyhFmdzzkTyaQNRH/upgmlRU5W7GPhc1skbrB/f/nnWm7K3rbogPzKIYGBtpptpFUVy0xtMqzJjSYkb8/ahcLw/EqZVCfIEfvV9Mzq0eojhVksGfDZjAjMdPWCIruJ7O4e5r3Fu3/4kg/QGsFg+MYy0xYq7AjZgSANNoGm29WB7V4k7oNdpDnTy1FXaFTLrG9jwcrW7reEQA2oAO8bHnzmqq7w24pggHzE61C3aTFc4X/L19ajbjl86l1/0ip0PZYpfJqaFbRlBHny96p7N/wA6vOzWDOIxFmwPxuAY5LuxHooNXZFGz7NYS1asKBEt4iBLGW2mJ5RT+Odo7WEZEuW7pZlLDKvKYk8xrP0NemYbhVlPltgfX6TWJufFXAozKtjEnKzLmW2pU5SVlWz6gxvWXKzTjRm07dWD/wBHEe1uf7mucGwH8SxTYlkcYa2FRAwCl2ADMuUtAGZjOpkKvXS/v/GDCx4cPiR5sg09g2tB8P8AinhLFkW7eGv+EHKMglmMtLMW3ZiSW6sTRbCkG8c4uljE4XCBQb2IcAKWHhtyZdgnWCB5g9Kl7eWWXhmK8Qju4ygPEFlXnA51i8L8Ysal5BiFti1P3ndoS0RPhk8zAmi+2XxWwWMwV7DKt9TdCrJQaDvEJO/8oNLY6RY/CEYJMHLXgt92Y3QIzABiqAkjbKAY/qNaDjOHxty4DgMVZW0FEh7TXGza6yukbV5+O0HZdwA3D7ug5K0/Vbgq97P/ABD4JgkZcHhcQgYjN92TJ2Eszk0MCu7c8S4pYRMPins3bWIMFktlNEdC1szr4gQPMM3St/2W4njC94YlbbIhVU7i2V8RXM05m2AKge/SvG+2nbh+J4m0wtm1as5hbUmWJYqWdo0/CoAHQ9a3lv4uYTC/cmxdZgSSwygMTuYmY5a9KTGS/Ev4kYjBX7dnDosm3nuC4NRLELsdJytp6dazHZfs4OKNiOIYxLxvu02lAa3ZYd2ApzxJXZfCdIJO4jJ8b7QG9xO5jHt5ovBhafUZLRCrbeDHyqJA5k1sbnxYt3cTZv28G3eqO7E3/BlY6winUnqdqqtCMx214XgrSWTh8/2pWjEhC72F0IZVZ9ZDQN9ddOlDi7RCXVDbZGMfKVZyOXKclaH4idoLmLu946vaCwvdm6GtA5ZlQIgms+t5AhY3AAbRESNSqnwH1al6sZS3EJEfX2pmS4ec8vb1p4xincRU4YRmnTaeVACF+94fHOUAD0EkTPSY9hRVviuIXaPp+xodSORHtrUgFMQcvHr5EZVHnB/VqScWxB/GTHkP2oNCRRFu91AP9/qKLCgtuLXCDmg6RqKAe5rVgTZOxKnoR+tMODQ/jH1H70xEIuHlVtwPi9/DP3thwtyCoZlDZQxEwDpMCJ6E1nFxVTrf03iqEbm78QeLMCv2pIIIP3K8xGkbe1P7D4O0Hm6qtatqFAZXcSdASE10AO/MisTavc82gr2jsHw66uCTKqjvT3pJ3IIhQegygGPM0tIZNcvcGtrmuDDBSYl7Los6mBm3Oh08qGbi3AP/ANE+sD9DQ/HuP4Nb5s4tbbvagR3Vy4FzKrQCEgGCs1W8V7Q8KNq53Fle+yMLY+yuF7wiFzMVgCYJ8hUgGcLw3DLpu3rmGwjC457lGhctpB3YKgKZzsHeeYZazPZ7huFxPEcVeNiwmGt/d27bZUtl/lnXc+Fj/nWtdwftRwpbC23udyyKFl7BLBgIzq4BBM6g1T8P4zwHA4fu7d1sWZJ1sBmJgCJYALoAPagZy1w/BPxrB4S3grDWwlx7oVVyHMjEMxA8QULoDpLjyrf8c7NW7RRcFwjBXQ4bvC4t21XLlyggKSZltQDGXzrzr4edquGYe9fx+LvMmKulkFpbTslqzKhVUokE5UUb7KPOi7/xHwP8aTFi9fbDdx3beFwquc2otQCRESYJlvKpY0XHxUscKwuFNpcNat4px9wLVoKysSAbmZVAyjn10G9XeHwGGw3CbeIu4Cwby4e2cjW0LPdZVVLbOyklmdlUkyZbnWW7Ydr+CcQvYcXDfdbRJFxEuKBLITbVCAWLlVBJEAAwQSDVz237W2b9gWrSXhdS7auLmXIga3cDeM6yBE5RuQNRSehoItdlMHgMPf4jjbFq7iIa84yg20Y6izh0IygAkIGIk7k6xRvw8x38SwZvYrC4dfvXW2ETw5EywfFJzBswkR8vKs9x34icLxuHu4TGDEWfEA/drn8Vpw023UGVzL+JQfIVUcV+KlmxhVwnCrL2wq5Fu3YGUc2VJJZjJMvGpkg0+xHo3ZLBWwmIzJaNtMReW02WT3aEKcxPNbguLpyQc5rDfDbtNc4lxXEP3FkYe3ZZUhNVTvZTn87DVtP+mIA5jdividhLWCXB4y3dOVWQ3EhhcViZzQwcOcxkgGd51qnufELAcPsXbHB8K9t7p8V68QSIEAqCzFoBMAwATMHWSqHZ6Xwh8HxHF4kXEs3PsV42rVqAYOVM151OjHOGVTGmQ8zVzxXheMa6Gs4u2tkABsNcsK6MBuM4IZZ9wOhrxrhvbXgv2WzhrmDxBdUUviLfdJfN7dnV1u5yCS27bHara58ZsJhLHc4OxirriYbFXJgnWS2d2IH8untSAxHxVx2LvcQuC7bS21hUshLTFkCgZwVJVdDnnYbgcqxxv3V0Mj1FSY/i969fuYi5cJuXGLsepPl05AcgKT8TvOMoO+miiTTAaOIHmop6cR/p/OgmQgkEEEbgjUetcAosCx/iKncGnfxBfOgUszzrpw7dKexDhT7ZJodT509TVAWFtl51ucF8QOJsMqYpUUaKosWYAGgA8M6DT2rz/Dkc6ucBdC6gUCLXE37ju73GLO7M7tAEsxk6chyA6CtZ8L+DWcTi3S9Z71BaLakgKcyAHTcmTpPWsbiMaIG7Hp09a3PYTtxg8DZObDYprzx3jKtrJoWyqma6DoDrpvPKKH0JG4XsnY+1G3/C7IwwX/HNzxFsswtoSYnSSRsfKfKO1XAMPiOLLg+F2mWQBckPkRwSXeH1CKuWeROg1Oum4f2+wOEuYi/YweNuXrxLN31y0FkszwMrtlEsfwkgacoqDhXxQwNtr2IfC4j7XiABduJ3cDKuVUtFrkhVAXWJJEkVGyjYYTsHws4a7ZTCpcuWVa0bty342u92GLhjvq240BBA2rzT4L9jcLjnu3MT4xaCZbOYjMWBOd4MlRAEbEzPnf8AZr4u2bGHS3dtYy/dgG47ujS8AEJmeQkjQQPqao+H9qeFpaFn+GXAM9xu+S4iYhM1wui27gIaApCmWHy7GdAZ6BxvswyXfu8Dw04VPEqGwUvSFkRcAZR45MlQI05zVLwftfhb/dWMPh0a/cbvLrOgCWgzs7JmYDMF8FsFZnNIDbGt4p8SGt4fusHYuKQrKL2Jum7dTMZkatmO8ZmhY2I0rOdhu1+F4erl8M969clTc7wAC2Y8EeZBJPPTpRXyFm14r2j4RhcY9u9YsggZiyWFchmuNNogKQIVFkwGOfca1fcCtWX4aMWcPgke4He2LiItsG5cbuVuOToTmtqQPQDlXjHH+I2MRjTijhYtEpmsC4RnCoqmXAGUsRrlA+utari3xZw+Is/Z7vCi9kFSLf2o2wMkZR93bBgRMTH5UUBb8a7apgLt3C4rB4O/eVEbNh7QtrbZ0LZGFwsTHgMiNDtW0XCLY4ZYLJgbOKNqygbEBVtd6VWVJPiZozaTJIrya78SsIbdqyOD2xbRg7Kb5Y3MskBnNrOfHlYyTOWDoTTe3HxRPELKWmwSW8lxbgY3Tc+UMMuXIu8wTO00AemdtMXbwPCycdhbN+44Ns91aVbPeNmyzmOZQB+LfTSJAqLhGL+wdnbeIu27fephwySm7XD90GB1Jl0n32rF8Y+MX2q2tu5w+yUkF1e6XzCCCFm2MkgkTqYJA3kP4n8W+/ti1e4Xh7lsEEI10lZXbw93GnSigPQOzi28fwhb/E7FmHS47nIFAtgtlcc1OQBpB6GufDjh3D0wNtMFdRbr2x3lxchxAuMupZXBIIJMKwIHSK8p4v8AFXG3ntZrVtMPadHNi3mUXO7IZVd98oIHhAAMazUl74tLcurfv8Kwl2+hm3dkhlCnMu6kkqecjXURRQGx+L/23CcMdWxSX1vOtp3e0tu+FPjChrcIw8BB8IMMfb57rVdtu22M4mym+VCJJS1bBCKT+IySWaNJPnESaytICa08UXbxUCgAaQppgOsWyzBRuxAE9SYrQL2NxM/NaBifmbaJn5enWmcB4fYZVdnyuDPzAbEwYPmBWjwl/Nc1xLZcluCTbkZ2cEfLyCge1AGet9mL4YrNvwhWJLGMr7GcvTWjk4BeRQ7G3lgndjIC5j+HpVvxgotp7y4gm4ihkM2zmIJAkBdQAzCKnssrrbHf6MAurW/DLFCPln5Of9WnlSYmiK3wu8BGTWNtOg138x9aHbAXuSecabEwDv1ke1W/Dj3lpGN8yU1EpyhYMr5flQty8VuIgvkKWZcxKHKqqHB+WPmYmfUVVk0V7YC8JOQ6Ak7ab/sfpQl/gN1mMLBEzPUEA7c9R9a0eLTwsFvNqGG6GRE8l0kswp9hE1ZsQQTnJ8SbztqvktKxmUHBbkZvCBAbc89eldPA7xGkbkDU7qSp5dQaveMCAoS6TncIdVIAnKPwxESPWKNNlAsLiSu5BzW/mZ2PNepk+tIDM4fAXnOWAD4hJ2YowRgNDMFhQWO7O3kDN4cqgsSCdAAGPLeCPzrVWVTwziTo14TmTQd6xkeHmUXWhcfifBHe6M9pGUlDmRyVYHwxosek0DM3/CLyCTGWROu0mOY8j9KKbs3dLKpCS4JGrbAgGTl0+YfnVziUttYgXSQZDDMnUg7CdmY+QNGXETRziCGVDlbNb8JY6geHbQTSAyLdnruYhSpiJ1MasViY6g1E/Ar4LAJmy6GDoD01jWtRw5la0l1r/wB4yrPitCfDmmMukMY66VJhiGtC7357xlViM1sZs2UkQVjUA6+VAGPu8HvgEm2RlidtJOUbHqYorDcBxBAjLrsCTO5EbeVam4ttrjIb5ysMxOa2MxFxiDoukFVPmaj4niTbVSl4nxWxvbmCwBHy7iT6bk0AZp+FXyqnJOadFOukbz6j613Ddlb1wKwyAMxUZiQQylgQYHVGHtWrKqpt5cSdyCM1vw/du2ng1BKpqelQsAl+zbTEEoVLk5kMOXSfwwP8Rz7npQBmX7P3VKL4CWzRBb8KM5mVEGFOnptUo7KXi5t/d5gAfmOxbLoY6kfWtILdtrgDXz4JZGm2PwKN8sa942nr0moLV0fabs4kjKqKrhreoZXffLB8SIfL3NAGZxfZ5rVrvGIjoJP4gu5Eb1UvbrcXrKXUdXxEibgAzWxmC3jlPhWflANZbjtlLd3LbOZYmZnmw/sBQBNwG+Bbjuy/ik/LHIxqRyH51ZWcQTaCC2QU8LGUjMU0/FruKq+EWrvdgq6Aa6FZO550Utq9JTOmuUzk0kyu88uflFIYXhXvpa7u9a+U27a5O75+EBvFqSRv71oCtxyCuGuRmnXuRPyrl0adIJHOW6VSYLEW3tzfxQR82oGVZ7u5KkSp5r1rRYLidgtlOOy+6SCOgCEz+1MRy4+ZUvC0VRSCxJtjwqpBiG3kUY5JClbLsCS0/cwfCyQNZ5mgVyt3lmzeW5aWBJBkq6yG0tECZOxonPiEWM6qiAmYaAFBn/o+RNUIi7vxqww7GG7w62oKiy9o5fFrJIb2qJrxQ2wbJksoGtuGIDE6htNjqdNBrQOHx993bJisPaW2q5TdOXNnBfwnLrqg1IEBhUl21efL/wDMwBKFjl7yACA66ArqCOWg1FABlm2wUJ3LZj3jFvu5Km5nn5ujgevpQ1m1ft24uW4CZVUp3ZjXKc0nUyUj3owLfJzfbMD8rD/EOqsBvKzsB561DjEu3Ea2+NweRwBpcgk5g0GRp4o1oAkvq+cn7O4GYSs2gIKsoHz66+1NSUmcK0FrfOzICqtsH59PEJqhbgRUq6YzBFlafFeO4MjTLr7e00V/8osScZw5crf9yNnBBBC6glJ01g+YpDCsfne5aIwxABuGC1rWEa2Y8cTmYHl5VHYtP3mXuM+hMA25DEaHV9dhzoPBcRa7dyvi7XfBntALYa4hUGS6PIBByA/Sj7eFxKjvhiFBYAGbP/iJ+fQDMTPMAdRSAnvYk21uvcwz5R44mycigyCRnncculBcQw+K723dt2Yg3JzNaMq7qQAM5EwIo3E8JxTqytiUHeZleLI27uD+PaNNulZngeES3cKpjAL2Z0KGyzRkYkEGY1yA6bTQMvsXd0Kmxd1Zf+xsLmWPm3zEDTzqTFYjumN18OyIEQHW0TmX7QzaK/S4PpXBhsU6i4b6hsqvHc6TK3QpOffN/aq3iuLxKi0GvWWD5n8a92B3TppMmSSAY9aAIsJiFxd233dp3Fq4XIHdqfEBljM2vjXUbQPOri9iYtm73NyMoY62NozSBJ67RVRg7t83Xv27lsMqoo7sI6QXLCS7qZkToDvUuA+0XleybqKqkWjNuTGQc1f+3SgA3C4p2CsLLMpXNJNv5XzFSALmhmNNxUFlbi3i32Y6ZlKza0ZreHM/PH4Cf8wrq4S8gFtb6+FVA+6GoGYCfHvAOvU+VBWMRecBu/E3QGMWxocqLE5v5SKQD+J4wC2xawcpVQf8PfPMaN5/mayHEHVnm2uVYiIA69CfKr9sVcYurXEgdAJkHdhm8Iqj4hfJck5df5TIj1FMQFNLMeprlKpGaDg+PKW1C3kSGzEMJJgsYOux+vnRuGx4WYv2oOQazPgBUS2aTI5HTWIgkVkq7NMDc4firLcL/a7YDNmZI8MlYP4swGk79POjL/Fy6hVxVq2R+ISdBOhzvGpMyOledZjSznrTsVF9x1gH0urcGUCVM6hVBnpJnnVUj60NJrk0cgo1Q7X3suX7vaPk8vWagftZfkmLev8AR6+em5rOTXcxo5BRbcU45dvqFcLAM+FY1gjr5mq7J1iosx61yixky3MplSQRsQYP5U84+7Ed7cjaM7bbRvtoPpQ1KlYBR4je/wC9c/1t+9QC6wObMc2usmdd9fc0ylSAIbH3jobtwj/zb96jvX3f5mZo2zEnf1qOlQA5HI2JHoYoixxC6gIV9zJkA67bkHpQtKgA8cXvfz//AFX16Uz+I3f5v/qvP2oOlTAne8xJJJk78v7VEabSmiwP/9k="));

        setsimlarMainRecyler(simlarTVShowList);


    }

    private void setsimlarMainRecyler(List<SimilarTvShows> simlarTVShowList) {

        similarTVShowRecylerview = findViewById(R.id.tvsimilarmoviesrecylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        similarTVShowRecylerview.setLayoutManager(layoutManager);
        simlarTVShowAdapter = new SimilarTVShowsAdapter(this, simlarTVShowList);
        similarTVShowRecylerview.setAdapter(simlarTVShowAdapter);
    }


    private void setMainRecyler(List<TvShowsCast> tvShowsCasts) {
        TVShowcastRecylerview = findViewById(R.id.Tvcastrecylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        TVShowcastRecylerview.setLayoutManager(layoutManager);
        castMoviesAdapter = new TvShowCastAdapter(this, tvShowsCasts);
        TVShowcastRecylerview.setAdapter(castMoviesAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,parent.getSelectedItem().toString(),Toast.LENGTH_LONG).show();


    }

    private void setBannerMoviesPagerAdapter(List<Episode> episodeItemList) {
        EpisodeRecylerview = findViewById(R.id.episoderecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        EpisodeRecylerview.setLayoutManager(layoutManager);
        episodeRecylerViewAdapter = new EpisodeRecylerViewAdapter(this, episodeItemList);
        bar.setVisibility(View.INVISIBLE);
        constraintLayout.setVisibility(View.VISIBLE);
        EpisodeRecylerview.setAdapter(episodeRecylerViewAdapter);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}