package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_users.*
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.recylerview.UsersAdapter
import life.league.challenge.kotlin.utilities.DeviceUtils
import life.league.challenge.kotlin.viewModel.UsersViewModel


class UsersActivity : AppCompatActivity() {

    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        manager = LinearLayoutManager(this)
        recycler_view_users?.apply {
            layoutManager = manager
        }

        ib_logout.setOnClickListener {
            DeviceUtils.logoutUser(this)
        }

        val model: UsersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        model.users?.observe(this,
            { heroList ->
                recycler_view_users?.apply {
                    myAdapter = UsersAdapter(heroList)
                    adapter = myAdapter
                }
            })
    }

    override fun onResume() {
        super.onResume()
        if (!DeviceUtils.isNetworkAvailable(this)) {
            DeviceUtils.showAlertDialog(this)
        }
    }
}