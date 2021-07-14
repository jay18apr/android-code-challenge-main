package life.league.challenge.kotlin.utilities

import android.app.Application
import android.util.Log

class ChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        private const val TAG = "ChallengeApplication"
        private var application: ChallengeApplication? = null
        val instance: ChallengeApplication?
            get() {
                if (application == null) Log.e(TAG, "Fatal error - app null")
                return application
            }
    }
}