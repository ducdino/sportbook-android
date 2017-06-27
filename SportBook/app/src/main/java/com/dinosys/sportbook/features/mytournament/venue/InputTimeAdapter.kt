package com.dinosys.sportbook.features.mytournament.venue

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dinosys.sportbook.R
import com.dinosys.sportbook.networks.models.TimeVenueUIModel
import java.lang.ref.WeakReference

class InputTimeAdapter(val inputTimeList: List<TimeVenueUIModel>?, val onTimeBlockListener: WeakReference<OnTimeBlocksListener>) : RecyclerView.Adapter<InputTimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InputTimeViewHolder? {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_my_tournament_input_time, parent, false)
        return InputTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: InputTimeViewHolder, position: Int) {
        holder.bindView(inputTimeList?.get(position), position, onTimeBlockListener)
    }

    override fun getItemCount(): Int = this.inputTimeList?.size ?: 0

}