package com.shivangi.globalinvestai

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.shivangi.globalinvestai.data.repository.StockRepository
import com.shivangi.globalinvestai.data.repository.StockRepositoryImpl
import com.shivangi.globalinvestai.ui.components.BottomNav
import com.shivangi.globalinvestai.ui.screens.*
import com.shivangi.globalinvestai.ui.theme.AppTheme
import com.shivangi.globalinvestai.ui.viewmodel.AiAssistantViewModel
import com.shivangi.globalinvestai.ui.viewmodel.DiscoverViewModel
import com.shivangi.globalinvestai.ui.viewmodel.HomeViewModel
import com.shivangi.globalinvestai.ui.viewmodel.PortfolioViewModel
import com.shivangi.globalinvestai.ui.viewmodel.StockDetailViewModel
import moe.tlaster.precompose.PreComposeApp
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single<StockRepository> { StockRepositoryImpl() }
    factory { HomeViewModel(get()) }
    factory { PortfolioViewModel(get()) }
    factory { params -> StockDetailViewModel(params.get(), get()) }
    factory { DiscoverViewModel(get()) }
    factory { AiAssistantViewModel() }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(appModule)
    }
}


@Composable
fun App() {
    PreComposeApp {
        AppTheme {
            Navigator(LoginScreen) { navigator ->
                // Define screens that should not have a bottom bar
                val screensWithoutBottomNav = listOf(
                    LoginScreen::class,
                    OnboardingScreen::class,
                    RegistrationScreen::class,
                    KycPanAadhaarScreen::class,
                    KycDigiLockerScreen::class,
                    KycSuccessScreen::class
                )
                val showBottomBar = navigator.lastItem::class !in screensWithoutBottomNav

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNav(navigator)
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        CurrentScreen()
                    }
                }
            }
        }
    }
}