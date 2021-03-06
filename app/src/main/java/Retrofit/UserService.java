package Retrofit;

import java.util.List;


import Model.AllLogin.LoginRequest;
import Model.AllLogin.LoginResponse;

import Model.AllMovies.Main;
import Model.AllMovies.PostMovies;
import Model.AllSearch.BothSearch;
import Model.AllTvshows.PostTVShows;
import Model.AllTvshows.ModelTvShowDetail;
import Model.MovieGenere.MovieGenere;
import Model.Search.Search;
import Model.TvSearch.TvShowSearch;
import Model.TvShowGenere.TvGenere;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    //TODO Replace with your API's Login Method
    @POST("login")
    Call<LoginResponse> Userlogin(@Body LoginRequest loginRequest);


//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("get/tv-shows")
//    Call<UserProfile> getTvShows(@Header("Authorization") String BearerToken);


    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("tv-shows")
    Call<List<PostTVShows>> getTvShows(@Field ("order_by") String order_by, @Field ("order") String order);

    @GET("{id}")
    Call<ModelTvShowDetail> getTvShowDetail(@Path("id") int id);

    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("articles")
    Call<List<PostMovies>> getMovies(@Field ("order_by") String order_by, @Field ("order") String order);

    @Headers("Accept:  application/json")
    @GET("{id}")
    Call<Main> getMoviesDetail(@Path("id") int id);

    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("articles")
    Call<Search> getSearch(@Field("title") String title);



    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("shows")
    Call<TvShowSearch> getTvSearch(@Field("title") String title);

    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("tv-shows")
    Call<TvGenere> gettvgenere(@Field ("order_by") String order_by, @Field ("order") String order,@Field("genre") String genre);

    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("articles")
    Call<MovieGenere> getmoviegenere(@Field ("order_by") String order_by, @Field ("order") String order, @Field("genre") String genre);
    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("articles?page=")
    Call<MovieGenere> getmoviepage( @Query("page") int page,
                                    @Field ("order_by") String order_by, @Field ("order")
                                            String order, @Field("genre") String genre
                                    );

    @Headers("Accept:  application/json")
    @FormUrlEncoded
    @POST("all_search")
    Call<BothSearch> getBoth(@Field("title") String title);

}