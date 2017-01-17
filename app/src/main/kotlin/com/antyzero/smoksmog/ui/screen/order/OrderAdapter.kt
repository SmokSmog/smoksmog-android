package com.antyzero.smoksmog.ui.screen.order

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import com.antyzero.smoksmog.R
import com.antyzero.smoksmog.settings.SettingsHelper
import pl.malopolska.smoksmog.model.Station
import pl.malopolska.smoksmog.utils.StationUtils
import java.util.*

class OrderAdapter(private val stationList: MutableList<Station>, private val onStartDragListener: OnStartDragListener, private val settingsHelper: SettingsHelper) : RecyclerView.Adapter<OrderItemViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(stationList[position])
        holder.viewHandle.setOnTouchListener { view, event ->
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                onStartDragListener.onStartDrag(holder)
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return stationList.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                Collections.swap(stationList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(stationList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)

        settingsHelper.stationIdList.let {
            it.clear()
            it.addAll(StationUtils.convertStationsToIdsList(stationList))
        }
    }

    override fun onItemDismiss(position: Int) {
        stationList.removeAt(position)
        notifyItemRemoved(position)
        settingsHelper.stationIdList.let {
            it.clear()
            it.addAll(StationUtils.convertStationsToIdsList(stationList))
        }
    }
}
