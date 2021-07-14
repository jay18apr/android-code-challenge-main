package life.league.challenge.kotlin.utilities

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import life.league.challenge.kotlin.utilities.ChallengeApplication.Companion.instance

object IntentSharedPrefUtils {
    private const val PREFERENCE_NAME = "life.league.challenge.kotlin"
    private val appContext: Context? = instance

    private fun getSharedPreferences(context: Context?): SharedPreferences {
        return context!!.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE)
    }

    fun removePreference(name: String?) {
        removePreference(appContext, name)
    }

    private fun removePreference(context: Context?, name: String?) {
        if (context != null && name != null) {
            val editor = getSharedPreferences(context).edit()
            editor.remove(name)
            editor.apply()
        }
    }

    fun getStringPreference(name: String?, defaultValue: String?): String? {
        return getStringPreference(appContext, name, defaultValue)
    }

    fun getStringPreference(context: Context?, name: String?, defaultValue: String?): String? {
        return getSharedPreferences(context).getString(name, defaultValue)
    }

    fun getIntPreference(name: String?, defaultValue: Int): Int {
        return getIntPreference(appContext, name, defaultValue)
    }

    fun getIntPreference(context: Context?, name: String?, defaultValue: Int): Int {
        return getSharedPreferences(context).getInt(name, defaultValue)
    }

    fun addPreference(name: String?, value: String?) {
        addPreference(appContext, name, value)
    }

    fun addPreference(context: Context?, name: String?, value: String?) {
        if (context != null && name != null) {
            val editor = getSharedPreferences(context).edit()
            editor.putString(name, value)
            editor.apply()
        }
    }

    fun addPreference(name: String?, value: Int) {
        addPreference(appContext, name, value)
    }

    fun addPreference(context: Context?, name: String?, value: Int) {
        if (context != null && name != null) {
            val editor = getSharedPreferences(context).edit()
            editor.putInt(name, value)
            editor.apply()
        }
    }
}