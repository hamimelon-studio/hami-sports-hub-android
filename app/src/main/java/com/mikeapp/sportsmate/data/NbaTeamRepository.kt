package com.mikeapp.sportsmate.data

import com.mikeapp.sportsmate.data.local.AppSettings
import com.mikeapp.sportsmate.data.mapper.NbaTeamDetailMapper.map
import com.mikeapp.sportsmate.data.network.service.NbaStatService
import com.mikeapp.sportsmate.data.network.service.NbaThemeService
import com.mikeapp.sportsmate.data.room.nba.TeamDetailDao
import com.mikeapp.sportsmate.data.room.nba.TeamDetailEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    private val nbaThemeService: NbaThemeService,
    private val nbaStatService: NbaStatService,
    private val teamDetailDao: TeamDetailDao
) {
    suspend fun fetchTeamDetailFromBackend(team: String) {
        withContext(Dispatchers.IO) {
            val response = nbaThemeService.getTeamDetail(team)
            val data = response.body()
            if (response.isSuccessful && data != null) {
                teamDetailDao.save(data.map())
            }
        }
    }

    suspend fun fetchSeasonStatusFromBackend() {
        withContext(Dispatchers.IO) {
            val response = nbaStatService.getSeasonStatus()
            val data = response.body()
            if (response.isSuccessful && data != null) {
                teamDetailDao.getTeamTheme()?.let { entity ->
                    entity.seasonStatus = data
                    teamDetailDao.save(entity)
                }
            }
        }
    }

    fun getTeamDetailFlow(): Flow<TeamDetailEntity> =
        teamDetailDao.getTeamThemeFlow().onEach { teamDetailEntity ->
            if (teamDetailEntity == null) {
                withContext(Dispatchers.IO) {
                    fetchTeamDetailFromBackend(AppSettings.myNbaTeam)
                    fetchSeasonStatusFromBackend()
                }
            }
        }.filterNotNull()

    fun debug(): String {
        val sb = StringBuilder()

        val themes = teamDetailDao.getAllRecords()
        sb.append("data.size: ${themes.size}")
        themes.forEach {
            sb.append("[${it.team}]${it.bannerUrl}")
        }

        return sb.toString()
    }
}