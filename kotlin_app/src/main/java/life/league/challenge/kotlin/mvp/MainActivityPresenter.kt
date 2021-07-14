package life.league.challenge.kotlin.mvp

import android.text.TextUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.api.login
import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.utilities.ChallengeApplication
import life.league.challenge.kotlin.utilities.DeviceUtils
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainActivityPresenter(private var view: MainActivityContract.View) :
    MainActivityContract.Presenter, CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun doLogin(username: String, password: String) {
        view.viewProgressbar()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            view.hideProgressbar(false)
            view.validateLogin()
        } else {
            if (ChallengeApplication.instance?.let { DeviceUtils.isNetworkAvailable(it) } == true) {
                this.launch(Dispatchers.Main) {
                    try {
                        val accountResponse: Response<Account> =
                            Service.api.login(username, password)
                        val account: Account?
                        if (accountResponse.isSuccessful) {
                            account = accountResponse.body()
                            account?.apiKey?.let {
                                view.hideProgressbar(true)
                                view.loginSuccess(it)
                            }
                        }
                    } catch (t: Throwable) {
                        view.hideProgressbar(false)
                        view.loginError()
                    }
                }
            } else {
                view.hideProgressbar(false)
                view.internetConnectionDialog()
            }
        }
    }

    override fun onStartHide() {
        view.hideSignInLayout()
    }

    override fun onStartVisible() {
        view.viewSignInLayout()
    }

    override fun viewDestroyed() {
        job.cancel()
    }

    override fun onStartHideProgressbar(flag: Boolean) {
        view.hideProgressbar(flag)
    }

    override fun onStartViewProgressbar() {
        view.viewProgressbar()
    }
}