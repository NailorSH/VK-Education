@file:OptIn(ExperimentalMaterial3Api::class)

package com.nailorsh.picsnet.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nailorsh.picsnet.R
import com.nailorsh.picsnet.ui.screens.HomeScreen
import com.nailorsh.picsnet.ui.screens.PhotoScreen
import com.nailorsh.picsnet.ui.screens.PicsViewModel

//enum class PicsNetScreen {
//    Home,
//    Photo
//}

@Composable
fun PicsNetApp(
    navController: NavHostController = rememberNavController(),
    picsViewModel: PicsViewModel = viewModel(factory = PicsViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PicsTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
//                startDestination = PicsNetScreen.Home.name,
                startDestination = "home",
            ) {
                composable(route = "home") {
                    HomeScreen(
                        picsUiState = picsViewModel.picsUiState,
                        retryAction = picsViewModel::getPhotos,
                        onCardClicked = { imgId -> navController.navigate("photo/$imgId") }
                    )
                }

                composable(
                    route = "photo/{id}",
                ) { backStackEntry ->
                    backStackEntry.arguments?.getString("id")?.let { id ->
                        PhotoScreen(
                            uiState = picsViewModel.picsUiState,
                            photoId = id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PicsTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}