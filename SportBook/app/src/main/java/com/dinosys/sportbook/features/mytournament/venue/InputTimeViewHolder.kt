package com.dinosys.sportbook.features.mytournament.venue

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.dinosys.sportbook.R
import com.dinosys.sportbook.networks.models.TimeVenueUIModel
import com.dinosys.sportbook.utils.ToastUtil
import kotlinx.android.synthetic.main.item_my_tournament_input_time.view.*
import java.lang.ref.WeakReference

class InputTimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(timeVenueUIModelModel: TimeVenueUIModel?, position: Int, onTimeBlockListener: WeakReference<OnTimeBlocksListener>) = with(itemView) {

        val arrayDays = itemView.resources.getStringArray(R.array.array_time_range_days)
        if (timeVenueUIModelModel?.isHeader!!) {
            tvTimeBlockLeft.visibility = View.INVISIBLE
            renderTimeRangeHeaderUI(tvSunday, arrayDays[0].substring(0, 1))
            renderTimeRangeHeaderUI(tvMonday, arrayDays[1].substring(0, 1))
            renderTimeRangeHeaderUI(tvTuesday, arrayDays[2].substring(0, 1))
            renderTimeRangeHeaderUI(tvWednesday, arrayDays[3].substring(0, 1))
            renderTimeRangeHeaderUI(tvThursday, arrayDays[4].substring(0, 1))
            renderTimeRangeHeaderUI(tvFriday, arrayDays[5].substring(0, 1))
            renderTimeRangeHeaderUI(tvSaturday, arrayDays[6].substring(0, 1))

        } else {
            tvTimeBlockLeft.visibility = View.VISIBLE
            tvTimeBlockLeft.text = timeVenueUIModelModel.timeBlock

            renderTimeRangeItemUI(tvSunday, timeVenueUIModelModel, arrayDays[0], onTimeBlockListener)
            renderTimeRangeItemUI(tvMonday, timeVenueUIModelModel, arrayDays[1], onTimeBlockListener)
            renderTimeRangeItemUI(tvTuesday, timeVenueUIModelModel, arrayDays[2], onTimeBlockListener)
            renderTimeRangeItemUI(tvWednesday, timeVenueUIModelModel, arrayDays[3], onTimeBlockListener)
            renderTimeRangeItemUI(tvThursday, timeVenueUIModelModel, arrayDays[4], onTimeBlockListener)
            renderTimeRangeItemUI(tvFriday, timeVenueUIModelModel, arrayDays[5], onTimeBlockListener)
            renderTimeRangeItemUI(tvSaturday, timeVenueUIModelModel, arrayDays[6], onTimeBlockListener)
        }
    }

    private fun renderTimeRangeHeaderUI(textView: TextView, text: String) {
        textView.setBackgroundColor(Color.WHITE)
        textView.visibility = View.VISIBLE
        textView.text = text;
    }

    private fun renderTimeRangeItemUI(textView: TextView, timeVenueUIModelModel: TimeVenueUIModel, day: String, timeVenueRef: WeakReference<OnTimeBlocksListener>) {
        textView.setOnClickListener {
            timeVenueRef.get()?.OnTimeBlockClick(day = day, blockTime = timeVenueUIModelModel.timeBlock!!)
        }
        if (timeVenueUIModelModel.isAvailableBlockTime(day)) {
            textView.setBackgroundResource(R.drawable.rounded_textview)
        } else {
            textView.setBackgroundResource(R.color.colorWhite)
        }
    }
}