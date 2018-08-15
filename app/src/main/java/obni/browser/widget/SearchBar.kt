package obni.browser.widget

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import obni.browser.R
import kotlinx.android.synthetic.main.search_bar.view.*
import obni.browser.util.hideKeyboard

class SearchBar(context: Context?, attrs: AttributeSet?) : CardView(context!!, attrs),
        View.OnClickListener {

    private val ANIMATION_SCALE = 150L
    var onSearch: OnSearch? = null

    init {
        inflate(context, R.layout.search_bar, this)
        cleanBtn.setOnClickListener(this)
        searchBtn.setOnClickListener(this)
        searchEdit.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                actionSearch()
                return@OnEditorActionListener true
            }
            false
        })
        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty()) {
                    searchBtn.setColorFilter(ContextCompat.getColor(context!!, R.color.gray400),
                            PorterDuff.Mode.SRC_ATOP)
                    exitClear()
                } else {
                    searchBtn.setColorFilter(ContextCompat.getColor(context!!, R.color.gray800),
                            PorterDuff.Mode.SRC_ATOP)
                    enterClear()
                }
            }

        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            cleanBtn.id -> {
                searchEdit.text.clear()
            }
            searchBtn.id -> {
                actionSearch()
            }
        }
    }

    private fun actionSearch() {
        if (onSearch != null && searchEdit.text.isNotEmpty()) {
            onSearch!!.search(searchEdit.text.toString().trim())
        }
        hideKeyboard(context!!, searchEdit)
    }

    private fun enterClear() {
        if (cleanBtn.visibility == View.INVISIBLE) {
            cleanBtn.visibility = View.VISIBLE
            val anim = ScaleAnimation(0f, 1f, 0f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f)
            anim.fillAfter = true
            anim.duration = ANIMATION_SCALE
            cleanBtn.startAnimation(anim)
        }
    }

    private fun exitClear() {
        if (cleanBtn.visibility == View.VISIBLE) {
            val anim = ScaleAnimation(1f, 0f, 1f, 0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f)
            anim.fillAfter = true
            anim.duration = ANIMATION_SCALE
            anim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    cleanBtn.visibility = View.INVISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {
                }

            })
            cleanBtn.startAnimation(anim)
        }
    }

    fun setSearch(text: String){
        searchEdit.setText(text)
    }

    interface OnSearch {
        fun search(q: String)
    }
}