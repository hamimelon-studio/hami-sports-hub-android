package com.mikeapp.sportshub.data.epsn.nfl

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EspnNflApiService {
    //https://www.espn.com/nfl/team/schedule/_/name/kc/kansas-city-chiefs
    @GET("/nfl/team/schedule/_/name/{teamAbbr}/{team}")
    suspend fun getTeamSchedule(
        @Path("teamAbbr") teamAbbr: String,
        @Path("team") team: String
    ): Response<ResponseBody>
}