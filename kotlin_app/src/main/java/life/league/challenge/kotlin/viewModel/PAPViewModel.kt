package life.league.challenge.kotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.model.PAP
import life.league.challenge.kotlin.utilities.IntentSharedPrefConstant
import life.league.challenge.kotlin.utilities.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PAPViewModel : ViewModel() {
    private var userList: MutableLiveData<List<PAP>>? =
        null
    val userPosts: LiveData<List<PAP>>?
        get() {
            if (userList == null) {
                userList = MutableLiveData<List<PAP>>()
                UserSession.getInstance()?.currentUserSession?.let {
                    loadUserPostsAlbumsPhotos(it, IntentSharedPrefConstant.POSTS)
                }
            }
            return userList
        }

    val userAlbums: LiveData<List<PAP>>?
        get() {
            if (userList == null) {
                userList = MutableLiveData<List<PAP>>()
                UserSession.getInstance()?.currentUserSession?.let {
                    loadUserPostsAlbumsPhotos(it, IntentSharedPrefConstant.ALBUMS)
                }
            }
            return userList
        }

    val userPhotos: LiveData<List<PAP>>?
        get() {
            if (userList == null) {
                userList = MutableLiveData<List<PAP>>()
                UserSession.getInstance()?.currentUserSession?.let {
                    loadUserPostsAlbumsPhotos(it, IntentSharedPrefConstant.PHOTOS)
                }
            }
            return userList
        }

    private fun loadUserPostsAlbumsPhotos(api_key: String, posts: String) {
        var call: Call<List<PAP>>? = null

        if (posts == IntentSharedPrefConstant.POSTS) {
            call = Service.api.postsRequest(api_key, UserSession.getInstance()?.currentUserId)
        }

        if (posts == IntentSharedPrefConstant.ALBUMS) {
            call = Service.api.albumsRequest(api_key, UserSession.getInstance()?.currentUserId)
        }

        if (posts == IntentSharedPrefConstant.PHOTOS) {
            call = Service.api.photosRequest(api_key)
        }

        call?.enqueue(object : Callback<List<PAP>> {
            override fun onResponse(call: Call<List<PAP>>, response: Response<List<PAP>>) {
                userList?.value = response.body()
            }

            override fun onFailure(call: Call<List<PAP>>, t: Throwable?) {}
        })
    }
}