package com.dinosys.sportbook.features.mytournament.detail

import android.os.Bundle
import android.widget.ArrayAdapter
import com.dinosys.sportbook.R
import com.dinosys.sportbook.extensions.openScreenByTag
import com.dinosys.sportbook.features.BaseFragment
import com.dinosys.sportbook.features.mytournament.opponent.OpponentFragment
import com.dinosys.sportbook.features.mytournament.timetable.TimeTableFragment
import com.dinosys.sportbook.features.mytournament.venue.TimeRankVenueFragment
import kotlinx.android.synthetic.main.fragment_my_tournament_registered.*

class TournamentDetailFragment : BaseFragment() {

    var idTournament: Int? = null
    var idTeam: Int? = null

    override fun inflateFromLayout(): Int = R.layout.fragment_my_tournament_registered

    override fun initViews() {
        idTournament = this.arguments.getInt(KEY_ID)
        idTeam = this.arguments.getInt(TEAM_ID)
        lvTournamentDetail.adapter = ArrayAdapter<String>(context, R.layout.item_mytournament_detail, resources.getStringArray(R.array.array_my_tournament_details_menu))
    }

    override fun initListeners() {
        super.initListeners()
        lvTournamentDetail.setOnItemClickListener { adapterView, view, i, l ->
            val bundle = Bundle();
            when (i) {
                0 -> {
                    bundle.putInt(TimeRankVenueFragment.KEY_ID, idTournament!!)
                    bundle.putInt(TimeRankVenueFragment.TEAM_ID, idTeam!!)
                    fragmentManager.openScreenByTag(tag = TimeRankVenueFragment.TAG, bundle = bundle)
                }
                1 -> {
                    bundle.putInt(TimeRankVenueFragment.KEY_ID, idTournament!!)
                    fragmentManager.openScreenByTag(tag = TimeRankVenueFragment.TAG, bundle = bundle)
                }
                2 -> {
                    bundle.putInt(TimeTableFragment.KEY_ID, idTournament!!)
                    fragmentManager.openScreenByTag(tag = TimeTableFragment.TAG, bundle = bundle)
                }
                3 -> {
                    bundle.putInt(OpponentFragment.KEY_ID, idTournament!!)
                    fragmentManager.openScreenByTag(tag = OpponentFragment.TAG, bundle = bundle)
                }
                4 -> {
                    bundle.putInt(TimeRankVenueFragment.KEY_ID, idTournament!!)
                    fragmentManager.openScreenByTag(tag = TimeRankVenueFragment.TAG, bundle = bundle)
                }
            }
        }

    }

    companion object {
        val TAG = "TournamentDetailFragment"
        val KEY_ID = "tournament_id"
        val TEAM_ID = "team_id"
    }
}
