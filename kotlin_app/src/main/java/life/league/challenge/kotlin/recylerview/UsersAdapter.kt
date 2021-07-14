package life.league.challenge.kotlin.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.model.Users
import life.league.challenge.kotlin.utilities.DeviceUtils
import life.league.challenge.kotlin.utilities.IntentSharedPrefConstant

class UsersAdapter(private val data: List<Users>) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    class UsersViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: Users) {
            val userName: TextView = itemView.findViewById(R.id.tv_username)
            val userAvatar: ImageView = itemView.findViewById(R.id.iv_user_avatar)
            val viewAllPostLink: TextView = itemView.findViewById(R.id.tv_view_all_post)
            val viewAllAlbumLink: TextView = itemView.findViewById(R.id.tv_view_all_albums)
            val viewAllPhotosLink: TextView = itemView.findViewById(R.id.tv_view_all_photos)

            userName.text = user.username
            Glide.with(view.context).load(user.avatar?.large).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(RequestOptions.circleCropTransform()).error(R.drawable.nopreviewimage)
                .placeholder(R.drawable.nopreviewimage).into(userAvatar)

            viewAllAlbumLink.setOnClickListener {
                DeviceUtils.openPostsAlbumsPhotosActivity(
                    view.context,
                    user,
                    IntentSharedPrefConstant.ALBUMS
                )
            }

            viewAllPostLink.setOnClickListener {
                DeviceUtils.openPostsAlbumsPhotosActivity(
                    view.context,
                    user,
                    IntentSharedPrefConstant.POSTS
                )
            }

            viewAllPhotosLink.setOnClickListener {
                DeviceUtils.openPostsAlbumsPhotosActivity(
                    view.context,
                    user,
                    IntentSharedPrefConstant.PHOTOS
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitems, parent, false)
        return UsersViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(data[position])
    }
}