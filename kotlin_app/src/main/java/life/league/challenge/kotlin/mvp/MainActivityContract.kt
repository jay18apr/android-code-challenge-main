package life.league.challenge.kotlin.mvp

interface MainActivityContract {
    interface View {
        fun validateLogin()
        fun loginSuccess(apikey: String)
        fun loginError()
        fun hideProgressbar(flag: Boolean)
        fun viewProgressbar()
        fun hideSignInLayout()
        fun viewSignInLayout()
        fun internetConnectionDialog()
    }

    interface Presenter {
        fun doLogin(username: String, password: String)
        fun onStartHide()
        fun onStartVisible()
        fun viewDestroyed()
        fun onStartHideProgressbar(flag: Boolean)
        fun onStartViewProgressbar()
    }
}