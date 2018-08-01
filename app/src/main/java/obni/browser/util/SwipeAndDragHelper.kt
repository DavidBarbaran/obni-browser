package obni.browser.util

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log

/**
 * Created by David on 24/07/2018.
 */
class SwipeAndDragHelper(var contract: ActionCompletionContract) : ItemTouchHelper.Callback() {

    var dragListener: DragListener? = null

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or
                ItemTouchHelper.END
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        Log.e("POSITION","${viewHolder!!.itemView.y}  -  ${viewHolder!!.itemView.x}")
        if (target!!.adapterPosition != 6) {
            contract.onViewMoved(viewHolder!!.adapterPosition, target.adapterPosition)
        }
        return true
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        if (dragListener != null){
            dragListener!!.onFinishDrag()
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {

    }


    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    interface ActionCompletionContract {
        fun onViewMoved(oldPosition: Int, newPosition: Int)
    }

    interface DragListener {
        fun onFinishDrag()
    }
}