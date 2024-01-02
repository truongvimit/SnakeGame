package com.truongvim.snakegame.di

import com.truongvim.snakegame.data.repository.RemoteConfigRepo
import com.truongvim.snakegame.data.repository.RemoteConfigRepoImpl
import com.truongvim.snakegame.presentation.activity.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MainViewModel(remoteConfigRepo = get()) }
}

val repositoryModule = module {
    single<RemoteConfigRepo> { RemoteConfigRepoImpl() }
}

val ktorModule = module {
    single {
        HttpClient {
            /*defaultRequest {
                url.takeFrom(URLBuilder().takeFrom("https://rss.applemarketingtools.com"))
            }*/
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { "https://rss.applemarketingtools.com" }
}