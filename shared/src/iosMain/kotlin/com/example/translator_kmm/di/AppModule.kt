package com.example.translator_kmm.di

import com.example.translator_kmm.database.TranslateDatabase
import com.example.translator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.example.translator_kmm.translate.data.local.DatabaseDriverFactory
import com.example.translator_kmm.translate.data.remote.HttpClientFactory
import com.example.translator_kmm.translate.data.tranlate.KtorTranslateClient
import com.example.translator_kmm.translate.domain.history.HistoryDataSource
import com.example.translator_kmm.translate.domain.translate.Translate
import com.example.translator_kmm.translate.domain.translate.TranslateClient

class AppModule {
    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase.invoke(DatabaseDriverFactory().create())
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase :Translate by lazy {
        Translate(translateClient,historyDataSource)
    }
}