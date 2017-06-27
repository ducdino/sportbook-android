package com.dinosys.sportbook.features.mytournament.venue

import android.content.Context
import com.dinosys.sportbook.networks.teams.TeamsAPI
import io.reactivex.Observable
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

import javax.inject.Inject

class TimeRankVenueViewModel @Inject constructor(val teamsAPI: TeamsAPI) {

    fun updateTimeSlotsModel(context: Context?, preferredTimeBlock: JSONObject,
                             venueRanking: JSONArray, teamId: Int?): Observable<Response<JSONObject>> {
        return teamsAPI.updateTimeSlots(preferredTimeBlock, venueRanking, teamId)
    }

}