package com.meet.project.equationmaster.ui.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.meet.project.equationmaster.R
import com.meet.project.equationmaster.network.models.History
import com.meet.project.equationmaster.ui.theme.ThemeColor
import com.meet.project.equationmaster.ui.theme.ThemeTypography
import com.meet.project.equationmaster.ui.CustomTopAppBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryActivityScreen(
    viewModel: MainViewModel,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ThemeColor.Secondary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            CustomTopAppBar(
                title = stringResource(id = R.string.history),
                onBackClick = onBackClick,
                onHistoryClick = {},
                showBackArrow = true
            )

            if (viewModel.showEmptyScreen) {
                EmptyScreen()
            } else {
                HistoryScreenContent(
                    viewModel = viewModel
                )
            }
        }

    }
}

@Composable
fun HistoryScreenContent(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.history.forEachIndexed { index, history ->
            item(key = "HistoryItem_$index") {
                HistoryItem(history = history)
            }
        }
    }
}

@Composable
fun HistoryItem(history: History) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = history.id.formatDateFromLong(),
            style = ThemeTypography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            color = ThemeColor.Primary,
            textAlign = TextAlign.Start
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, ThemeColor.Grey50),
            color = ThemeColor.Secondary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                history.answers.forEachIndexed { i, answer ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        text = "${i + 1}) ${history.equationsList[i].replace(" ", "")} = $answer",
                        style = ThemeTypography.subtitle2,
                        color = ThemeColor.Primary,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

fun Long.formatDateFromLong(): String {
    val df = SimpleDateFormat("dd MMM yyyy HH:MM", Locale.ENGLISH)
    return df.format(Date(this))
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_empty_history),
                contentDescription = "empty",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "No data yet!",
                style = ThemeTypography.subtitle1,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
