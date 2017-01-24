package com.antyzero.smoksmog.ui.screen.order

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.SmokSmog
import com.antyzero.smoksmog.storage.model.Item
import pl.malopolska.smoksmog.utils.StationUtils
import java.util.*

class OrderAdapter(smokSmog: SmokSmog, private val idNameMap: Map<Long,String>, private val onStartDragListener: OnStartDragListener) : RecyclerView.Adapter<OrderItemViewHolder>(), ItemTouchHelperAdapter {

    val stationList = smokSmog.storage

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(idNameMap[stationList.fetchAll()[position].id])
        holder.viewHandle.setOnTouchListener { view, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onStartDragListener.onStartDrag(holder)
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return arrayListOf(stationList.fetchAll().size, idNameMap.size).min() ?: 0
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

        val listCopy = mutableListOf<Item>().apply { addAll(stationList.fetchAll()) }

        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                Collections.swap(listCopy, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listCopy, i, i - 1)
            }
        }
        stationList.set(listCopy)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        stationList.removeAt(position)
        notifyItemRemoved(position)
    }
}
