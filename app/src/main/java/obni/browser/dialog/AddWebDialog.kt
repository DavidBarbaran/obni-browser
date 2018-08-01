package obni.browser.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import obni.browser.R

/**
 * Created by David on 25/07/2018.
 */
class AddWebDialog(context: Context) : AlertDialog(context) {

    lateinit var selectOption: SelectOption

    init {
        val layoutInflater = LayoutInflater.from(getContext())
        val view = layoutInflater.inflate(R.layout.dialog_add_web, null)



        setView(view)
        //window.setBackgroundDrawableResource(android.R.color.transparent)
        //window.attributes.gravity = Gravity.TOP


        val params = window.attributes
        params.y = -100
        window.attributes = params
    }

    interface SelectOption {
        fun addWeb()
        fun cancel()
    }
}