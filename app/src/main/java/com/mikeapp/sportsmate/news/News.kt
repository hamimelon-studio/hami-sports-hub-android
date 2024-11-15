package com.mikeapp.sportsmate.news

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mikeapp.sportsmate.ui.animation.ErrorView
import com.mikeapp.sportsmate.ui.animation.LoadingContent
import com.mikeapp.sportsmate.ui.component.DataStatus
import com.mikeapp.sportsmate.ui.component.DataStatusSnackBar
import com.mikeapp.sportsmate.ui.component.TeamLogo
import com.mikeapp.sportsmate.util.LocalDateTimeUtil

@Preview
@Composable
fun News() {
    val viewModel = hiltViewModel<NewsViewModel>()
    val dataStatus = viewModel.dataStatus.observeAsState().value
    val transactions = viewModel.transactions.observeAsState().value

    // TODO
//    SwipeRefresh(
//        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
//        onRefresh = { viewModel.refresh() },
//    ) {
        DataStatusSnackBar(viewModel.dataStatus.observeAsState().value)
        if (transactions != null) {
            LazyColumn {
                transactions.forEach { transaction ->
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            TeamLogo(
                                logoUrl = transaction.teamLogo,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = LocalDateTimeUtil.getLocalDateDisplay(
                                    transaction.unixTimeStamp
                                )
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = transaction.description)
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        } else {
            when (dataStatus) {
                is DataStatus.ServiceError -> ErrorView(dataStatus.message)
                else -> LoadingContent()
            }
        }
//    }
}