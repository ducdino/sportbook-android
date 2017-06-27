package com.dinosys.sportbook.features.mytournament

import android.support.v7.widget.RecyclerView
import android.view.View
import com.dinosys.sportbook.features.tournament.OnTournamentListener
import com.dinosys.sportbook.networks.models.TournamentDataModel
import kotlinx.android.synthetic.main.item_my_tournament.view.*
import java.lang.ref.WeakReference

open class MyTournamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(tournament: TournamentDataModel, position: Int, tournamentReference: WeakReference<OnTournamentListener>) = with(itemView) {

        itemView.setOnClickListener {
            tournamentReference.get()?.onTournamentClick(tournament)
        }
        tvTournamentName.text = tournament.name
        tvTournamentTime.text = tournament.startDate
        tvRegisterStatus.text = tournament.teams?.get(0)?.status
        //tvRegisterTime.text = teamDataModel.created_at
        //ivTournament.loadFromUrl(tournament.image_url)
    }
}