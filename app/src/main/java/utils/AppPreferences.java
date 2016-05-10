package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by macb on 11/05/16.
 */
public class AppPreferences {
    public static SharedPreferences sharedPreferences;
    public static String TOKEN_KEY;

    public static void init(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(ctx.getPackageName(), Context.MODE_PRIVATE);
    }
    public static void setAccessToken(String token){
        sharedPreferences.edit().putString(TOKEN_KEY, token).commit();
    }
    public static String getAccessToken(){
        return  sharedPreferences.getString(TOKEN_KEY, null);
    }

}
