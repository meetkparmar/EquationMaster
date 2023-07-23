package com.meet.project.equationmaster.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.meet.project.equationmaster.R
import com.meet.project.equationmaster.ui.theme.ThemeColor
import com.meet.project.equationmaster.ui.theme.ThemeTypography


@Composable
fun getScreenWidth(): Dp {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp.dp
}

@Composable
fun CustomTopAppBar(
    showBackArrow: Boolean = false,
    title: String,
    onBackClick: () -> Unit,
    onHistoryClick: () -> Unit,
) {
    TopAppBar(
        backgroundColor = ThemeColor.Primary,
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackArrow) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBackClick() },
                    tint = ThemeColor.Secondary,
                )
            }
            Text(
                text = title,
                modifier = Modifier
                    .width(getScreenWidth() - 80.dp)
                    .padding(horizontal = 16.dp),
                style = ThemeTypography.subtitle1,
                color = ThemeColor.Secondary,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
            if (!showBackArrow) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "history",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onHistoryClick() },
                    tint = ThemeColor.Secondary,
                )
            }
        }
    }
}