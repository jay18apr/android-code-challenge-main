import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("large") var large: String,
    @SerializedName("medium") var medium: String,
    @SerializedName("thumbnail") var thumbnail: String
)