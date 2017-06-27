package com.dinosys.sportbook.networks.models

class TimeVenueUIModel(val isHeader: Boolean?, val timeBlock: String?, var blockTimeRangeList: ArrayList<String>?) {

    fun isAvailableBlockTime(blockTime: String): Boolean {
        if (blockTimeRangeList == null || blockTimeRangeList!!.isEmpty()) {
            return false
        }
        if (blockTimeRangeList!!.contains(blockTime)) {
            return true
        }
        return false
    }
}

class RankVenueUIModel(val isHeader: Boolean?, val venueName: String, val venueDistanceTime: String)

