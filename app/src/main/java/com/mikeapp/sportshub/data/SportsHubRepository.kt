package com.mikeapp.sportshub.data

import com.mikeapp.sportshub.data.NetworkModule.espnNbaApiService
import com.mikeapp.sportshub.data.NetworkModule.espnNflApiService
import com.mikeapp.sportshub.data.epsn.CurlEspnService
import com.mikeapp.sportshub.data.epsn.nba.EspnNbaParser
import com.mikeapp.sportshub.data.epsn.nba.model.NbaTeamScheduleQuery
import com.mikeapp.sportshub.data.epsn.nfl.model.NflTeamScheduleQuery
import com.mikeapp.sportshub.data.epsn.nfl.EspnNflParser


class SportsHubRepository(
    private val curlEspnService: CurlEspnService,
    private val espnNbaParser: EspnNbaParser,
    private val espnNflParser: EspnNflParser
) {
    suspend fun queryNba(): NbaTeamScheduleQuery? {
        val response = espnNbaApiService.getTeamSchedule("gs", 2)
        if (response.isSuccessful) {
            val jsonString = curlEspnService.getTeamScheduleJson(response.body()?.string() ?: "")
            val teamSchedule = jsonString?.let { espnNbaParser.parse(jsonString) }
            return teamSchedule
        }
        return null
    }

    suspend fun queryNfl(): NflTeamScheduleQuery? {
        val response = espnNflApiService.getTeamSchedule("kc", "kansas-city-chiefs")
        if (response.isSuccessful) {
            val jsonString = curlEspnService.getTeamScheduleJson(response.body()?.string() ?: "")
            val teamSchedule = jsonString?.let { espnNflParser.parse(jsonString) }
            return teamSchedule
        }
        return null
    }
}