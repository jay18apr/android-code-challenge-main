package life.league.challenge.kotlin.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.mvp.MainActivityContract
import life.league.challenge.kotlin.mvp.MainActivityPresenter
import life.league.challenge.kotlin.utilities.ChallengeApplication
import life.league.challenge.kotlin.utilities.DeviceUtils
import life.league.challenge.kotlin.utilities.UserSession


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var presenter: MainActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)
        presenter?.onStartViewProgressbar()
        presenter?.onStartHide()
        if (UserSession.getInstance()?.currentUserSession?.isNotEmpty() == true) {
            presenter?.onStartHideProgressbar(true)
            goToUsersActivity()
        } else {
            presenter?.onStartHideProgressbar(false)
            presenter?.onStartVisible()
            et_username.setText(getString(R.string.username))
            et_password.setText(getString(R.string.password))

            btn_login.setOnClickListener {
                presenter?.doLogin(et_username.text.toString(), et_password.text.toString())
            }
        }
    }

    override fun validateLogin() {
        Toast.makeText(applicationContext, "Invalid Inputs", Toast.LENGTH_LONG).show()
    }

    override fun loginSuccess(apikey: String) {
        Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_LONG).show()
        UserSession.getInstance()?.updateUserApiKey(apikey)
        goToUsersActivity()
    }

    private fun goToUsersActivity() {
        val intent = Intent(this@MainActivity, UsersActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
        startActivity(intent)
        finish()
    }

    override fun loginError() {
        Toast.makeText(applicationContext, "Login Failure", Toast.LENGTH_LONG).show()
    }

    override fun hideProgressbar(flag: Boolean) {
        progressBar.visibility = View.GONE
        if (!flag) {
            et_username.visibility = View.VISIBLE
            et_password.visibility = View.VISIBLE
            btn_login.visibility = View.VISIBLE
        }
    }

    override fun viewProgressbar() {
        progressBar.visibility = View.VISIBLE
        et_username.visibility = View.GONE
        et_password.visibility = View.GONE
        btn_login.visibility = View.GONE
    }

    override fun hideSignInLayout() {
        et_username.visibility = View.GONE
        et_password.visibility = View.GONE
        btn_login.visibility = View.GONE
    }

    override fun viewSignInLayout() {
        et_username.visibility = View.VISIBLE
        et_password.visibility = View.VISIBLE
        btn_login.visibility = View.VISIBLE
    }

    override fun internetConnectionDialog() {
        ChallengeApplication.instance?.let { DeviceUtils.showAlertDialog(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.viewDestroyed()
    }

    override fun onResume() {
        super.onResume()
        if (!DeviceUtils.isNetworkAvailable(this)) {
            DeviceUtils.showAlertDialog(this)
        }
    }

}