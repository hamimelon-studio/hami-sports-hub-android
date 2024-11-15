package com.mikeapp.sportsmate.data.mapper

import com.mikeapp.sportsmate.data.network.model.nba.NbaTransactionsResponse
import com.mikeapp.sportsmate.data.network.model.nba.TransactionResponse
import com.mikeapp.sportsmate.data.room.nba.NbaTransaction
import com.mikeapp.sportsmate.data.room.nba.NbaTransactionsEntity

object NbaTransactionsMapper {
    fun NbaTransactionsResponse.map(): NbaTransactionsEntity = NbaTransactionsEntity(
        dataVersion = dataVersion,
        transactions = transactions.map { it.map() }
    )

    private fun TransactionResponse.map(): NbaTransaction = NbaTransaction(
        unixTimeStamp = unixTimeStamp,
        description = description,
        teamAbbr = teamAbbr,
        teamDisplayName = teamDisplayName,
        teamLogo = teamLogo
    )
}