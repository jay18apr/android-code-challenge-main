package life.league.challenge.kotlin.utilities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import life.league.challenge.kotlin.main.PostsAlbumsPhotosActivity
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.model.Users


object DeviceUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return false
        }
    }

    fun showAlertDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Check your internet connection and try again.")
        builder.setTitle("No Connection")
        builder.setCancelable(true)
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.cancel()
        }

        val alert = builder.create()
        alert.show()
    }

    fun openPostsAlbumsPhotosActivity(context: Context, user: Users, selectedCategory: String) {
        val users = User()
        users.name = user.name
        users.avatar = (user.avatar?.let {

            when {
                it.large.isNotEmpty() -> {
                    it.large
                }
                it.medium.isNotEmpty() -> {
                    it.medium
                }
                it.thumbnail.isNotEmpty() -> {
                    it.thumbnail
                }
                else -> {
                    null
                }
            }
        } ?: run {
            null
        }).toString()

        user.id?.let {
            UserSession.getInstance()?.updateUserId(it)
            val intent = Intent(context, PostsAlbumsPhotosActivity::class.java)
            intent.putExtra(IntentSharedPrefConstant.USER, users)
            intent.putExtra(IntentSharedPrefConstant.CATEGORY, selectedCategory)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
            context.startActivity(intent)
        }
    }

    fun logoutUser(context: Context) {
        IntentSharedPrefUtils.removePreference(IntentSharedPrefConstant.API_KEY)
        IntentSharedPrefUtils.removePreference(IntentSharedPrefConstant.USEID)
        (context as AppCompatActivity).finishAffinity()
    }
}
