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
import life.league.challenge.kotlin.model.PAP
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.utilities.IntentSharedPrefConstant

class PostsAlbumsPhotosAdapter(
    private val data: List<PAP>,
    user: User,
    selectedCategory: String
) :
    RecyclerView.Adapter<PostsAlbumsPhotosAdapter.PAPViewHolder>() {

    private var currentUser: User = user
    private var currentSelectedCategory: String = selectedCategory

    class PAPViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(pap: PAP, currentUser: User, currentSelectedCategory: String) {
            val userName: TextView = itemView.findViewById(R.id.tv_username)
            val userAvatar: ImageView = itemView.findViewById(R.id.iv_user_avatar)
            val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
            val tvBody: TextView = itemView.findViewById(R.id.tv_body)
            val tvUrl: TextView = itemView.findViewById(R.id.tv_url)
            userName.text = currentUser.name

            Glide.with(view.context).load(currentUser.avatar).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(RequestOptions.circleCropTransform()).error(R.drawable.nopreviewimage)
                .placeholder(R.drawable.nopreviewimage).into(userAvatar)

            tvBody.visibility = View.GONE
            tvTitle.visibility = View.GONE
            tvUrl.visibility = View.GONE

            if (!pap.title.isNullOrEmpty()) {
                tvTitle.text = pap.title
                tvTitle.visibility = View.VISIBLE
            }
            if (currentSelectedCategory == IntentSharedPrefConstant.POSTS && !pap.body.isNullOrEmpty()) {
                tvBody.text = pap.body
                tvBody.visibility = View.VISIBLE
            }
            if (currentSelectedCategory == IntentSharedPrefConstant.PHOTOS) {
                tvUrl.text = pap.url
                tvUrl.visibility = View.VISIBLE
                Glide.with(view.context).load(pap.url).dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .apply(RequestOptions.circleCropTransform()).error(R.drawable.nopreviewimage)
                    .placeholder(R.drawable.nopreviewimage).into(userAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PAPViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listpapitems, parent, false)
        return PAPViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PAPViewHolder, position: Int) {
        holder.bind(data[position], this.currentUser, this.currentSelectedCategory)
    }

}