package com.example.marta.testobserverv2;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("auth/login")
    Call<User> login(@Body User login);

    @POST("users/")
    Call<User> register(@Body User user);

    @POST("groups/")
    Call<Group> createGroup(@Header("Authorization") String authToken, @Body Group reg);

    @POST("groups/{input}/add_by_name/")
    Call<Group> addUserToGroup(@Header("Authorization") String authToken, @Body Group reg, @Path("input") int input);

    @POST("join/")
    Call<Group> sendRequest(@Header("Authorization") String authToken, @Body Group reg);

    @POST("/groups/{input}/expenses/")
    Call<Transaction> postGroupTransaction(@Header("Authorization") String authToken, @Body Transaction pgt, @Path("input") int input);

    @POST("/groups/{input}/requests/{requestID}/accept/")
    Call<ResponseBody> acceptRequest(@Header("Authorization") String authToken, @Path("input") int input, @Path("requestID") int requestID);

    @POST("/groups/{input}/requests/{requestID}/decline/")
    Call<ResponseBody> declineRequest(@Header("Authorization") String authToken, @Path("input") int input, @Path("requestID") int requestID);

    @GET ("/groups/{input}/1/expense_total/")
    Call<ResponseBody> getSum(@Header("Authorization") String authToken, @Path("input") int input);

    @GET("groups/{input}/users/")
    Call<ResponseBody> getUsersList(@Header("Authorization") String authToken, @Path("input") int input);

    @GET("users/me/")
    Call<ResponseBody> getUserInformation(@Header("Authorization") String authToken);

    @GET("users/{input}/")
    Call<ResponseBody> getSelectedUserInformation(@Header("Authorization") String authToken, @Path("input") int input);
    @GET("categories")
    Call<ResponseBody> getCategories(@Header("Authorization") String authToken);

    @GET("/groups/{input}/expenses/")
    Call<ResponseBody> getGroupTransaction(@Header("Authorization") String authToken, @Path("input") int input);

    @GET("/groups/{input}/expenses/{expensesId}/")
    Call<ResponseBody> getTransactionDetail(@Header("Authorization") String authToken, @Path("input") int input, @Path("expensesId") int expensesId);

    @GET("/groups/{input}/requests/")
    Call<ResponseBody> getGroupRequests(@Header("Authorization") String authToken, @Path("input") int input);

    @DELETE("groups/{groupId}/users/{userId}/")
    Call<ResponseBody> removeUsersFromGroup(@Header("Authorization") String authToken, @Path("groupId") int groupId, @Path("userId") int userId);

    @DELETE("groups/{groupId}/expenses/{expensesId}/")
    Call<ResponseBody> removeTransaction(@Header("Authorization") String authToken, @Path("groupId") int groupId, @Path("expensesId") int expensesId);

    @PATCH("users/{input}/")
    Call<User> editMe(@Header("Authorization") String authToken, @Body User pgt, @Path("input") int input);

    @PATCH("groups/{groupId}/expenses/{input}/")
    Call<ResponseBody> changeConstantTransaction(@Header("Authorization") String authToken, @Body ConstantTransactionField pgt, @Path("groupId") int groupId, @Path("input") int input);
}