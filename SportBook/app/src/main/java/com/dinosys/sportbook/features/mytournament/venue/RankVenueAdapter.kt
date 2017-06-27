package com.dinosys.sportbook.features.mytournament.venue

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dinosys.sportbook.R
import com.dinosys.sportbook.networks.models.RankVenueUIModel
import com.dinosys.sportbook.networks.models.TimeVenueUIModel

class RankVenueAdapter(val rankVenues: List<RankVenueUIModel>?) : RecyclerView.Adapter<RankVenueViewHolder>() {

    override fun onBindViewHolder(holder: RankVenueViewHolder?, position: Int) {
        holder?.bindView(rankVenues?.get(position), position)
    }

    override fun getItemCount(): Int = this.rankVenues?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RankVenueViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_my_tournament_rank_venue, parent, false)
        return RankVenueViewHolder(view)
    }

}