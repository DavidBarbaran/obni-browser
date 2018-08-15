package obni.browser.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.search_bar.*
import obni.browser.R
import obni.browser.adapter.WebAdapter
import obni.browser.model.Web
import obni.browser.util.SwipeAndDragHelper
import obni.browser.util.hideKeyboard
import obni.browser.widget.SearchBar
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import obni.browser.config.Setting

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        searchBar.onSearch = object : SearchBar.OnSearch {
            override fun search(q: String) {
                val intent = Intent(this@HomeActivity, BrowserActivity::class.java)
                intent.putExtra(Setting.SEARCH, q)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeActivity, searchBar as View, "searchBar")
                startActivity(intent, options.toBundle())
            }
        }
        var adapter = WebAdapter(list())
        adapter.dragListener = object : WebAdapter.DragListener {
            override fun onDragStart() {
                hideKeyboard(this@HomeActivity, searchEdit)
                webRecycler.elevation = 15f
                Log.e("onDragStart", "start")
            }
        }
        val swipeAndDragHelper = SwipeAndDragHelper(adapter)
        swipeAndDragHelper.dragListener = object : SwipeAndDragHelper.DragListener {
            override fun onFinishDrag() {
                webRecycler.elevation = 1f
                Log.e("onFinishDrag", "")
            }
        }
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)
        adapter.touchHelper = touchHelper
        webRecycler.layoutManager = GridLayoutManager(this, 4)
        webRecycler.adapter = adapter
        touchHelper.attachToRecyclerView(webRecycler)
    }

    private fun list(): MutableList<Web> {
        var list = mutableListOf<Web>()
        list.add(Web("Youtube", "www.facebook.com", "ic_facebook"))
        list.add(Web("Youtube", "www.youtube.com", "ic_youtube"))
        list.add(Web("Youtube", "www.twitter.com", "ic_twitter"))
        list.add(Web("Youtube", "www.instagram.com", "ic_instagram"))
        list.add(Web("Youtube", "www.blogger.com", "ic_blogger"))
        list.add(Web("Youtube", "www.tumblr.com", "ic_tumblr"))
        list.add(Web("Youtube", "", "ic_add"))
        return list
    }
}
