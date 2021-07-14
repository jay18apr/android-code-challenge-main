package life.league.challenge.kotlin.utilities

import life.league.challenge.kotlin.utilities.IntentSharedPrefUtils.addPreference
import life.league.challenge.kotlin.utilities.IntentSharedPrefUtils.getIntPreference
import life.league.challenge.kotlin.utilities.IntentSharedPrefUtils.getStringPreference

class UserSession {
    val currentUserSession: String?
        get() = getStringPreference(IntentSharedPrefConstant.API_KEY, null)

    fun updateUserApiKey(apikey: String?) {
        addPreference(IntentSharedPrefConstant.API_KEY, apikey)
    }

    val currentUserId: Int
        get() = getIntPreference(IntentSharedPrefConstant.USEID, 0)

    fun updateUserId(userId: Int) {
        addPreference(IntentSharedPrefConstant.USEID, userId)
    }

    companion object {
        private var instance: UserSession? = null
        private fun initInstance() {
            if (instance == null) {
                instance = UserSession()
            }
        }

        @JvmName("getInstance1")
        fun getInstance(): UserSession? {
            if (instance == null) {
                initInstance()
            }
            return instance
        }
    }
}