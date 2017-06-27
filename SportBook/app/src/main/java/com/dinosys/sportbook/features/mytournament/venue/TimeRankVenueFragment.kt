package com.dinosys.sportbook.features.mytournament.venue

import android.support.v7.widget.LinearLayoutManager
import com.dinosys.sportbook.MainActivity
import com.dinosys.sportbook.R
import com.dinosys.sportbook.application.SportbookApp
import com.dinosys.sportbook.extensions.appContext
import com.dinosys.sportbook.extensions.remove
import com.dinosys.sportbook.features.BaseFragment
import com.dinosys.sportbook.managers.AuthenticationManager
import com.dinosys.sportbook.networks.models.TimeVenueUIModel
import com.dinosys.sportbook.utils.LogUtil
import com.dinosys.sportbook.utils.ToastUtil
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_my_tournament_time_rank_venue_change.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import java.lang.ref.WeakReference
import javax.inject.Inject

class TimeRankVenueFragment : BaseFragment(), OnTimeBlocksListener {

    override fun inflateFromLayout(): Int = R.layout.fragment_my_tournament_time_rank_venue_change

    var timeVenueList: ArrayList<TimeVenueUIModel>? = null

    var timeVenueAdapter: InputTimeAdapter? = null

    var idTeam: Int? = null

    @Inject
    lateinit var timerankvenueApi: TimeRankVenueViewModel

    override fun initViews() {
        idTeam = this.arguments.getInt(TEAM_ID)
        timeVenueList = getList()
        timeVenueAdapter = InputTimeAdapter(timeVenueList, WeakReference(this))
        rvTimeVenue.adapter = timeVenueAdapter
        rvTimeVenue.layoutManager = LinearLayoutManager(context)
    }

    override fun initData() {
        SportbookApp.teamComponent.inject(this)
    }

    override fun initListeners() {
        super.initListeners()

        val btnUpdateTimeVenue = RxView.clicks(btnUpdateTimeVenue)
                .subscribeOn(AndroidSchedulers.mainThread())
                .switchMap {
                    val objecPreferredTimeBlocks: JSONObject = JSONObject()
                    // TODO: Raniking Venue need implement later
                    val arrayRankingVenue: JSONArray = JSONArray("[1,2,3,4]")

                    activity.resources.getStringArray(R.array.array_time_range_days).forEach { day ->
                        val jsonArray = JSONArray()
                        timeVenueList?.filter { timeVenueUIModel -> !timeVenueUIModel.isHeader!! }
                                ?.filter { timeVenueUIModel -> timeVenueUIModel.isAvailableBlockTime(day) }
                                ?.forEach { timeVenueUIModel ->
                                    val timeBlockItemJsonArray = convertPreferTimeBlockItemToJSONArray(timeVenueUIModel.timeBlock!!)
                                    jsonArray.put(timeBlockItemJsonArray)
                                }
                        objecPreferredTimeBlocks.put(day, jsonArray)
                    }


                    timerankvenueApi.updateTimeSlotsModel(activity, objecPreferredTimeBlocks, arrayRankingVenue, 111)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorResumeNext {
                                t: Throwable? ->
                                onUpdateTimeRankErrorResponse(t?.message)
                            }


                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> onUpdateTimeRankDataResponse(response = response) })

        addDisposable(btnUpdateTimeVenue)
    }

    private fun onUpdateTimeRankDataResponse(response: Response<JSONObject>?) {
        val statusCode = response?.code()
        when (statusCode) {
            in 200..300 -> {
                val update = response?.body()
                LogUtil.e(TAG, update.toString())
            }
            else -> onUpdateTimeRankErrorResponse(getString(R.string.error_update_timerank_failure_text))
        }
    }

    private fun onUpdateTimeRankErrorResponse(message: String?): ObservableSource<out Response<JSONObject>>? {
        ToastUtil.show(appContext, message)
        return Observable.empty()
    }

    fun convertPreferTimeBlockItemToJSONArray(timeBlock: String): JSONArray? {
        if (timeBlock == activity.getString(R.string.time_block_9am_12am_text)) {
            return JSONArray("[9,10,11]")
        } else if (timeBlock == activity.getString(R.string.time_block_1pm_4pm_text)) {
            return JSONArray("[13,14,15]")
        } else if (timeBlock == activity.getString(R.string.time_block_5pm_7pm_text)) {
            return JSONArray("[17,18]")
        }
        return null
    }


    fun getList(): ArrayList<TimeVenueUIModel> {
        val items = ArrayList<TimeVenueUIModel>()
        //val days = ArrayList<String>()
        items.add(TimeVenueUIModel(true, "", null));
        items.add(TimeVenueUIModel(false, getString(R.string.time_block_9am_12am_text), null));
        items.add(TimeVenueUIModel(false, getString(R.string.time_block_1pm_4pm_text), null));
        items.add(TimeVenueUIModel(false, getString(R.string.time_block_5pm_7pm_text), null));

        return items
    }

    override fun OnTimeBlockClick(day: String, blockTime: String) {
        val timeVenue = timeVenueList?.filter { timeVenueUIModel -> timeVenueUIModel.timeBlock!!.equals(blockTime) }?.get(0)
        if (timeVenue == null) {
            return
        }
        if (timeVenue.blockTimeRangeList == null) {
            timeVenue.blockTimeRangeList = ArrayList<String>()
        }

        val blockTimeRangeList = timeVenue.blockTimeRangeList
        if (blockTimeRangeList!!.contains(day)) {
            blockTimeRangeList.remove(day)
        } else {
            blockTimeRangeList.add(day)
        }
        timeVenueAdapter?.notifyDataSetChanged()
    }

    companion object {
        val TAG: String = "TimeRankVenueFragment"
        val KEY_ID: String = "idTournament"
        val TEAM_ID: String = ""
    }

}