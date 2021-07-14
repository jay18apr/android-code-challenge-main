package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.PAP
import life.league.challenge.kotlin.model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Response<Account>

    @GET("users")
    fun usersRequest(@Header("x-access-token") api_key: String?): Call<List<Users>>

    @GET("posts")
    fun postsRequest(
        @Header("x-access-token") api_key: String?,
        @Query("userId") userId: Int?
    ): Call<List<PAP>>

    @GET("albums")
    fun albumsRequest(
        @Header("x-access-token") api_key: String?,
        @Query("userId") userId: Int?
    ): Call<List<PAP>>

    @GET("photos")
    fun photosRequest(@Header("x-access-token") api_key: String?): Call<List<PAP>>

}

/**
 * Overloaded Login API extension function to handle authorization header encoding
 */
suspend fun Api.login(username: String, password: String) = login(
    "Basic " + android.util.Base64.encodeToString(
        "$username:$password".toByteArray(),
        android.util.Base64.NO_WRAP
    )
)

