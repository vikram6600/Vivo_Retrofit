package com.example.vikrampatel.vivo.ApiInterface;

import com.example.vikrampatel.vivo.ApiResponse.RegisterResponse;
import com.example.vikrampatel.vivo.ApiResponse.LoginResponse;
import com.example.vikrampatel.vivo.ApiResponse.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("registration_controller.php")
    Call<LoginResponse> LOGIN_RESPONSE_CALL(
            @Field("login") String registration,
            @Field("mobile") String mobile,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("registration_controller.php")
    Call<RegisterResponse> REGISTER_RESPONSE_CALL(
            @Field("registers") String registration,
            @Field("First_name") String First_name,
            @Field("Last_name") String Last_name,
            @Field("gender") String gender,
            @Field("dob") String edob,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("registration_controller.php")
    Call<UpdateResponse> UPDATE_RESPONSE_CALL(
            @Field("update") String update,
            @Field("id")String id,
            @Field("First_name") String First_name,
            @Field("Last_name") String Last_name,
            @Field("gender") String gender,
            @Field("dob") String edob,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password);

}
