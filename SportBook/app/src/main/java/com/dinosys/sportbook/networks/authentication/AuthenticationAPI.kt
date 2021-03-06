package com.dinosys.sportbook.networks.authentication

import com.dinosys.sportbook.networks.models.AuthModel
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface AuthenticationAPI {

    @DELETE("auth/sign_out")
    fun singOut(@Header("Access-Token") accessToken: String?,
                @Header("Client") client: String?,
                @Header("Expiry") expiry: String?,
                @Header("Token-Type") tokenType: String?,
                @Header("Uid") Uid: String?): Observable<Response<JSONObject>>

    @FormUrlEncoded
    @POST("auth/sign_in")
    fun signIn(@Field("email")email:String, @Field("password") password:String): Observable<Response<AuthModel>>

    @FormUrlEncoded
    @POST("auth/sign_in_with_facebook")
    fun signInWithFacebook(@Field("access_token") accessToken:String): Observable<Response<JSONObject>>

    @FormUrlEncoded
    @POST("auth/password")
    fun forgotPassword(@Field("email") email : String,
                       @Field("redirect_url") redirectUrl : String) : Observable<Response<JSONObject>>


    @POST("auth/")
    fun signUp(@Field("email")email:String, @Field("password") password:String): Observable<Response<AuthModel>>

}