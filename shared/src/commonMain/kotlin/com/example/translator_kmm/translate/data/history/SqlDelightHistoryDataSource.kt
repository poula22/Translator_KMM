package com.example.translator_kmm.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.translator_kmm.core.domain.util.CommonFlow
import com.example.translator_kmm.core.domain.util.toCommonFlow
import com.example.translator_kmm.database.TranslateDatabase
import com.example.translator_kmm.translate.domain.history.HistoryDataSource
import com.example.translator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db:TranslateDatabase
): HistoryDataSource {
    val queries=db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { history->
                history.map { it.historyItemMapper() }
        }.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.toLanguageCode,
            toLanguageCode = item.toLanguageCode,
            fromText = item.fromText,
            toText = item.toText,
            timeStamp = Clock.System.now().toEpochMilliseconds()
        )
    }

}