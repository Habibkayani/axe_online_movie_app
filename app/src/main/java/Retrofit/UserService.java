package Retrofit;

import Model.LoginRequest;
import Model.LoginResponse;
import Model.UserProfile;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface UserService {

    //TODO Replace with your API's Login Method
    @POST("login")
    Call<LoginResponse> Userlogin(@Body LoginRequest loginRequest);


//    @Headers({"Content-Type: application/json;charset=UTF-8"})
//    @POST("get/tv-shows")
//    Call<UserProfile> getTvShows(@Header("Authorization") String BearerToken);

    @FormUrlEncoded
    @POST("tv-shows")
    @Headers("Accept:  application/json")
    Call<UserProfile> getUser(@Header("Authorization") String authHeader, @Field("start") int start,
                              @Field("limit") int limit,
                              @Field ("order_by") String order_by, @Field ("order") String order);
}