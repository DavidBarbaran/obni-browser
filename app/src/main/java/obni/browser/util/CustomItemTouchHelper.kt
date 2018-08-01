package obni.browser.util

import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MotionEvent

/**
 * Created by David on 26/07/2018.
 */
abstract class CustomItemTouchHelper(callback: Callback?) : ItemTouchHelper(callback) {

    abstract fun onTouchMovement(touch_event: MotionEvent, directionFlags: Int, pointerIndex: Int)


    fun updateDxDy(ev: MotionEvent?, directionFlags: Int, pointerIndex: Int) {

    }
}