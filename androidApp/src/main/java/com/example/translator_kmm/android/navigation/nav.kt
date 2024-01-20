package com.example.translator_kmm.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translator_kmm.android.core.presentation.Routes
import com.example.translator_kmm.android.translate.presentation.AndroidTranslateViewModel
import com.example.translator_kmm.android.translate.presentation.components.TranslateScreen

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.TRANSLATE ){
        composable(route = Routes.TRANSLATE){
            val viewModel:AndroidTranslateViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            TranslateScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}