package requests;


import objects.MainFeed;
import objects.Report;
import objects.UserProfile;
import objects.UserToken;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by etrittahiri on 5/10/16.
 */
public interface RequestCallBack {
    @FormUrlEncoded
    @POST("api/v1/login")
    Call<UserToken> userToken(@Field("fb_access_token") String fb_access_token,
                              @Field("device_id") String device_id,
                              @Field("device_os") String device_os,
                              @Field("push_token") String push_token);
    @GET("api/v1/user/{user_id}?")
    Call<UserProfile> getUserProfile(@Path("user_id") String id, @Query("token") String access_token);

    @GET("api/v1/items")
    Call<MainFeed> mainFeed(@Query("token") String access_token);

    @GET("api/v1/items/{report_id}")
    Call<Report> getReportById(@Path("report_id") String id, @Query("token") String access_token);
    @Multipart
    @POST("api/v1/items/create")
    Call<String> uploadReport(@Part("title") RequestBody title, @Part("description") RequestBody description, @Part("image\"; filename=\"image.jpg\" ") RequestBody photo, @Query("token") String access_token);

}
