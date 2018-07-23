package obni.browser.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_browser.*
import obni.browser.R
import obni.browser.widget.SearchBar

class BrowserActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        searchBar.onSearch = object : SearchBar.OnSearch {
            override fun search(q: String) {
                Toast.makeText(this@BrowserActivity, "Your search is: $q", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }

}
