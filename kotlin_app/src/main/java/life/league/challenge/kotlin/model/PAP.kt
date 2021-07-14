package life.league.challenge.kotlin.model

import com.google.gson.annotations.SerializedName

data class PAP(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("useId") var useId: Int? = null,
    @SerializedName("albumId") var albumId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String? = null
)