package Retrofit;

import java.util.List;

import Model.LoginRequest;
import Model.LoginResponse;

import Model.PostTVShows;
import Model.ModelTvShowDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    Call<List<PostTVShows>> getTvShows(@Field("start") int start,
                                       @Field("limit") int limit,
                                       @Field ("order_by") String order_by, @Field ("order") String order);

    @GET("{id}")
    Call<ModelTvShowDetail> getTvShowDetail(@Path("id") int id);
}