package com.study.littlelemon.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.study.littlelemon.R
import com.study.littlelemon.navigation.Destinations
import com.study.littlelemon.ui.component.LittleLemonBtn
import com.study.littlelemon.ui.component.LogoBar
import com.study.littlelemon.ui.component.TextInputField
import com.study.littlelemon.ui.theme.LittleLemonTheme
import com.study.littlelemon.viewmodel.ProfileUiState
import com.study.littlelemon.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Profile(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val viewState: ProfileUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LogoBar()
        ProfileInputArea(viewState = viewState)
        LittleLemonBtn(
            modifier = Modifier
                .padding(start = 12.dp, end= 12.dp ,bottom = 24.dp)
                .height(48.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.profile_logout),
            onClick = {
                viewModel.handleLogout()
                navHostController.navigate(Destinations.ON_BOARDING.route) {
                    navHostController.popBackStack(route = Destinations.HOME.route, inclusive = true)
                    navHostController.graph.setStartDestination(Destinations.ON_BOARDING.route)
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun ProfileInputArea(
    viewState: ProfileUiState
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
    ) {
        Column {
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
                enabled = false,
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewState.lastName,
                label = stringResource(R.string.onboarding_last_name_label),
                enabled = false,
                onValueChange = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextInputField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewState.email,
                label = stringResource(R.string.onboarding_email_label),
                enabled = false,
                onValueChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Profile(rememberNavController())
        }

    }
}