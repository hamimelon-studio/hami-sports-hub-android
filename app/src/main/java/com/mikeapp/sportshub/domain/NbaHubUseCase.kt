package com.mikeapp.sportshub.domain

import com.google.gson.Gson
import com.mikeapp.sportshub.data.GithubOpenApiRepository
import com.mikeapp.sportshub.data.SportsHubRepository

class NbaHubUseCase(
    private val repository: SportsHubRepository,
    private val githubRepository: GithubOpenApiRepository
) {
    suspend fun queryOnce() {
        val teamSchedule = repository.queryNba()
        teamSchedule?.let {
            val gson = Gson()
            val jsonString = gson.toJson(teamSchedule)
            githubRepository.createOrForceUpdateFile(
                "nba/gs.json",
                jsonString,
                "dataVersion from sports-hub-android",
            )
        }
    }
}