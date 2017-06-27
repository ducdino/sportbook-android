package com.dinosys.sportbook.networks.teams

import com.dinosys.sportbook.networks.models.TeamModel
import io.reactivex.Observable
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface TeamsAPI{

    @GET("teams/{id}/time_slots")
    fun getTimeSlots(): Observable<Response<TeamModel>>

    @FormUrlEncoded
    @PUT("teams/{team_id}")
    fun updateTimeSlots(@Field("preferred_time_blocks") preferredTimeBlocks: JSONObject?,
                        @Field("venue_ranking") venueRanking: JSONArray?,
                        @Path("team_id") teamId: Int?): Observable<Response<JSONObject>>
}