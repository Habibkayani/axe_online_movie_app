package Retrofit

import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface user {



    @Multipart

    @POST("user/channel/create/update/{id}")
    fun sendMessageToChannel(


            @Header("Authorization") token : String?,
            @Part("id") channelId: Int,
            @Part("message")message: RequestBody?,
            @Part file: List<MultipartBody.Part>


    )

}