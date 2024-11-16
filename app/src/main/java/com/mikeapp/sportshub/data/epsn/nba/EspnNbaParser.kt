package com.mikeapp.sportshub.data.epsn.nba

import com.google.gson.Gson
import com.mikeapp.sportshub.data.epsn.nba.model.NbaTeamScheduleQuery

class EspnNbaParser {
    fun parse(json: String): NbaTeamScheduleQuery {
        val gson = Gson()
        val query: NbaTeamScheduleQuery = gson.fromJson(json, NbaTeamScheduleQuery::class.java)
        return query
    }
}