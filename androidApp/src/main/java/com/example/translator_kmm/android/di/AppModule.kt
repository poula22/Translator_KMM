package com.example.translator_kmm.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.example.translator_kmm.database.TranslateDatabase
import com.example.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.example.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.example.translator_kmm.translate.data.remote.HttpClientFactory
import com.example.translator_kmm.translate.data.tranlate.KtorTranslateClient
import com.example.translator_kmm.translate.domain.history.HistoryDataSource
import com.example.translator_kmm.translate.domain.translate.Translate
import com.example.translator_kmm.translate.domain.translate.TranslateClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase.invoke(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource,
    ): Translate {
        return Translate(client = client, historyDataSource = dataSource)
    }
}