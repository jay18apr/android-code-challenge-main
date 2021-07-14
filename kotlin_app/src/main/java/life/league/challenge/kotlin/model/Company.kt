package life.league.challenge.kotlin.model

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("name") var name: String,
    @SerializedName("catchPhrase") var catchPhrase: String,
    @SerializedName("bs") var bs: String
)