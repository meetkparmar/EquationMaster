package com.meet.project.equationmaster.ui.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.meet.project.equationmaster.R
import com.meet.project.equationmaster.ui.theme.ThemeColor
import com.meet.project.equationmaster.ui.theme.ThemeTypography
import com.meet.project.equationmaster.ui.CustomTopAppBar

@Composable
fun MainActivityScreen(
    viewModel: MainViewModel,
    onHistoryClick: () -> Unit,
    onButtonClick: () -> Unit,
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
                title = stringResource(id = R.string.app_name),
                onBackClick = { },
                onHistoryClick = onHistoryClick,
                showBackArrow = false
            )

            if (viewModel.showLoading) {
                LoadingScreen()
            } else if (viewModel.resultScreen) {
                ResultScreen(viewModel = viewModel)
            } else {
                MainScreenContent(viewModel = viewModel)
            }
        }

        Button(
            onClick = {
                if (viewModel.resultScreen) viewModel.clear() else onButtonClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(12.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = ThemeColor.Primary,
                contentColor = ThemeColor.Grey20
            )
        ) {
            Text(
                text = stringResource(id = if (viewModel.resultScreen) R.string.try_again else R.string.evaluate),
                style = ThemeTypography.button,
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Transparent),
                color = ThemeColor.Secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreenContent(
    viewModel: MainViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.equationText,
            onValueChange = { newText ->
                viewModel.onEquationTextChange(newText)
            },
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }),
            placeholder = {
                Text(
                    text = "Enter Equations",
                    style = ThemeTypography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    color = ThemeColor.Grey50,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = ThemeColor.Grey50,
                focusedBorderColor = ThemeColor.Grey50,
                textColor = ThemeColor.Primary,
                cursorColor = ThemeColor.Primary
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = true
        )

        if (viewModel.errorText.isNotBlank()) {
            Text(
                text = viewModel.errorText,
                style = ThemeTypography.caption,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        Text(
            text = stringResource(id = R.string.note),
            style = ThemeTypography.caption,
            color = ThemeColor.Grey50,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}

@Composable
fun LoadingScreen() {
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
            CircularProgressIndicator(
                color = ThemeColor.Primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.loading),
                style = ThemeTypography.subtitle1,
                color = ThemeColor.Primary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ResultScreen(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "topSpacer") {
            Spacer(modifier = Modifier.height(12.dp))
        }
        viewModel.equationsResponse?.result?.forEachIndexed { index, answer ->
            item(key = "answer$index") {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    text = "${index + 1}) ${viewModel.equationsList[index].replace(" ", "")} = $answer",
                    style = ThemeTypography.subtitle1,
                    color = ThemeColor.Primary,
                    textAlign = TextAlign.Start,
                )
            }
        }
        item(key = "bottomSpacer") {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

