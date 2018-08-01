package obni.browser.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_web.view.*
import obni.browser.model.Web
import obni.browser.R
import obni.browser.dialog.AddWebDialog
import obni.browser.util.SwipeAndDragHelper

/**
 * Created by David on 23/07/2018.
 */
class WebAdapter(private var webs: MutableList<Web>?) : RecyclerView.Adapter<WebAdapter.WebHolder>(),
        SwipeAndDragHelper.ActionCompletionContract {

    lateinit var touchHelper: ItemTouchHelper
    lateinit var dragListener: DragListener
    var isDragEnabled = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebHolder {
        return WebHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_web, parent,
                false))
    }

    override fun getItemCount(): Int {
        return if (webs == null) 0 else webs!!.size
    }

    override fun onBindViewHolder(holder: WebHolder, position: Int) {
        holder.itemView.webImage.setImageResource(
                holder.itemView.context.resources.getIdentifier(
                        webs!![holder.adapterPosition].image,
                        "drawable",
                        holder.itemView.context.packageName))
        if (isDragEnabled) {
            if (holder.adapterPosition != webs!!.size - 1) {
                holder.itemView.deleteBtn.visibility = View.VISIBLE
                holder.itemView.deleteBtn.setOnClickListener({
                    webs!!.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                })
            }
            holder.itemView.webImage.setOnTouchListener { _, p1 ->
                if (p1!!.actionMasked == MotionEvent.ACTION_DOWN &&
                        holder.adapterPosition != webs!!.size - 1) {
                    if (::dragListener.isInitialized) {
                        dragListener.onDragStart()
                    }
                    if (::touchHelper.isInitialized) {
                        touchHelper.startDrag(holder)
                    }
                }
                false
            }
        } else {
            holder.itemView.deleteBtn.visibility = View.INVISIBLE
        }

        if (holder.adapterPosition == webs!!.size - 1) {
            holder.itemView.webImage.setOnClickListener({
                val dialog = AddWebDialog(holder.itemView.context)
                dialog.show()

            })
        }
        /*
        holder.itemView.webImage.setOnTouchListener { _, p1 ->
            if (p1!!.actionMasked == MotionEvent.ACTION_DOWN) {
                if (touchHelper != null) {
                    touchHelper!!.startDrag(holder)
                }
            }
            false
        }
        */

        holder.itemView.setOnLongClickListener {
            if (holder.adapterPosition != webs!!.size - 1) {
                isDragEnabled = true
                notifyDataSetChanged()
            }
            false
        }
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        var targetWeb = webs!![oldPosition]
        val web = targetWeb
        webs!!.removeAt(oldPosition)
        webs!!.add(newPosition, web)
        notifyItemMoved(oldPosition, newPosition)
    }

    inner class WebHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface DragListener {
        fun onDragStart()
    }

}