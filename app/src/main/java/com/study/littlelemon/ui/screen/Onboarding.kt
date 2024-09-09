package com.study.littlelemon.ui.screen

import ScrollBarConfig
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.study.littlelemon.R
import com.study.littlelemon.navigation.Destinations
import com.study.littlelemon.ui.component.LittleLemonBtn
import com.study.littlelemon.ui.component.LogoBar
import com.study.littlelemon.ui.component.TextInputField
import com.study.littlelemon.ui.theme.LittleLemonTheme
import com.study.littlelemon.utils.rememberImeState
import com.study.littlelemon.viewmodel.OnBoardingUiState
import com.study.littlelemon.viewmodel.OnBoardingViewModel
import org.koin.androidx.compose.koinViewModel
import verticalScrollWithScrollbar


@Composable
fun Onboarding(
    navHostController: NavHostController,
    viewModel: OnBoardingViewModel = koinViewModel()
) {

    val viewState: OnBoardingUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context: Context = LocalContext.current
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val scrollState: ScrollState = rememberScrollState()
    val imeState: State<Boolean> = rememberImeState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LogoBar()

        Column(
            modifier = Modifier
                .verticalScrollWithScrollbar(
                    state = scrollState,
                    scrollbarConfig = ScrollBarConfig(
                        padding = PaddingValues(4.dp)
                    )
                )
                .weight(1f, fill = false)
                .imePadding()
        ) {
            OnBoardingBanner()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                OnBoardingInputArea(viewModel, viewState, scrollState, imeState)
                Spacer(modifier = Modifier.height(32.dp))
                LittleLemonBtn(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.onboarding_register),
                    onClick = {
                        val registerState: Boolean = viewModel.handleRegister()

                        keyboardController?.hide()

                        if (registerState) {
                            Toast.makeText(
                                context,
                                "Registration Success.",
                                Toast.LENGTH_LONG).show()

                            navHostController.navigate(Destinations.HOME.route) {
                                navHostController.popBackStack()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.onboarding_register_unsuccessful),
                                Toast.LENGTH_LONG).show()
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun OnBoardingBanner() {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(50.dp)
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.onboarding_title),
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun OnBoardingInputArea(
    viewModel: OnBoardingViewModel,
    viewState: OnBoardingUiState,
    scrollState: ScrollState,
    imeState: State<Boolean>,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.onboarding_personal_information),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            )
            Spacer(modifier = Modifier.height(32.dp))

            TextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewState.firstName,
                label = stringResource(R.string.onboarding_first_name_label),
                onValueChange = { viewModel.updateFirstName(it) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewState.lastName,
                label = stringResource(R.string.onboarding_last_name_label),
                scrollState = scrollState,
                imeState = imeState,
                onValueChange = { viewModel.updateLastName(it) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewState.email,
                label = stringResource(R.string.onboarding_email_label),
                scrollState = scrollState,
                imeState = imeState,
                onValueChange = { viewModel.updateEmail(it) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Onboarding(rememberNavController())
        }

    }
}