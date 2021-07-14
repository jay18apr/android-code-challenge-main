package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_users.*
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.model.User
import life.league.challenge.kotlin.recylerview.PostsAlbumsPhotosAdapter
import life.league.challenge.kotlin.utilities.DeviceUtils
import life.league.challenge.kotlin.utilities.IntentSharedPrefConstant
import life.league.challenge.kotlin.viewModel.PAPViewModel


class PostsAlbumsPhotosActivity : AppCompatActivity() {

    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private var selectedUser: User? = null
    private var selectedCategory: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        try {
            selectedUser = intent?.getSerializableExtra(IntentSharedPrefConstant.USER) as? User
            selectedCategory = intent?.getStringExtra(IntentSharedPrefConstant.CATEGORY)
            tv_header.text = selectedCategory
        } catch (exception: Exception) {
            finish()
        }

        ib_logout.setOnClickListener {
            DeviceUtils.logoutUser(this)
        }

        manager = LinearLayoutManager(this)
        recycler_view_users?.apply {
            layoutManager = manager
        }

        val model: PAPViewModel = ViewModelProvider(this).get(PAPViewModel::class.java)

        if (selectedCategory == IntentSharedPrefConstant.POSTS) {
            model.userPosts?.observe(this,
                { postsList ->
                    recycler_view_users?.apply {
                        myAdapter =
                            PostsAlbumsPhotosAdapter(postsList, selectedUser!!, selectedCategory!!)
                        adapter = myAdapter
                    }
                })
        }

        if (selectedCategory == IntentSharedPrefConstant.ALBUMS) {
            model.userAlbums?.observe(this,
                { postsList ->
                    recycler_view_users?.apply {
                        myAdapter =
                            PostsAlbumsPhotosAdapter(postsList, selectedUser!!, selectedCategory!!)
                        adapter = myAdapter
                    }
                })
        }

        if (selectedCategory == IntentSharedPrefConstant.PHOTOS) {
            model.userPhotos?.observe(this,
                { postsList ->
                    recycler_view_users?.apply {
                        myAdapter =
                            PostsAlbumsPhotosAdapter(postsList, selectedUser!!, selectedCategory!!)
                        adapter = myAdapter
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        if (!DeviceUtils.isNetworkAvailable(this)) {
            DeviceUtils.showAlertDialog(this)
        }
    }
}