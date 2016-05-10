package requests;

import objects.UserToken;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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
//    @GET("api/v1/items?")
//    Call<List<GetAllTrip>> getAllTrip(@Query("token") String access_token);
}
