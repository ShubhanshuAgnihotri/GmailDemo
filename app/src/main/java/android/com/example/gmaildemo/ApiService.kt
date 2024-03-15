package android.com.example.gmaildemo

import android.telecom.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/venues/search")
    fun getMatches(
        @Query("ll")latLng: String,
        @Query("oauth_token") oauthToken :String,
        @Query("v")version:String

    ): retrofit2.Call<MatchesResponse>

}
