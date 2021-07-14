package life.league.challenge.kotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.model.Users
import life.league.challenge.kotlin.utilities.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsersViewModel : ViewModel() {
    private var userList: MutableLiveData<List<Users>>? =
        null
    val users: LiveData<List<Users>>?
        get() {
            if (userList == null) {
                userList = MutableLiveData<List<Users>>()
                UserSession.getInstance()?.currentUserSession?.let {
                    loadUsers(it)
                }
            }
            return userList
        }

    private fun loadUsers(api_key: String) {
        val call: Call<List<Users>> = Service.api.usersRequest(api_key)
        call.enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                userList?.value = response.body()
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable?) {}
        })
    }
}