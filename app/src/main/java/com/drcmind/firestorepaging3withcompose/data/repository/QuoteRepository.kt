package com.drcmind.firestorepaging3withcompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.drcmind.firestorepaging3withcompose.data.paging.QuotesPagingDataSource
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val pagingDataSource: QuotesPagingDataSource
) {
    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {pagingDataSource}
    ).flow
}