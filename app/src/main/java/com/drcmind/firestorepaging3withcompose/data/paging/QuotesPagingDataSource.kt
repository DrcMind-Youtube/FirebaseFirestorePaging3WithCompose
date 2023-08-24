package com.drcmind.firestorepaging3withcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.drcmind.firestorepaging3withcompose.domain.model.Quote
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class QuotesPagingDataSource @Inject constructor(
    private val query: Query
) : PagingSource<QuerySnapshot, Quote>(){
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Quote>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Quote> {
        return try {
            val currentPage = params.key ?: query.get().await()
            val lastVisibleQuote = currentPage.documents[currentPage.size()-1]
            val nextPage = query.startAfter(lastVisibleQuote).get().await()

            LoadResult.Page(
                data = currentPage.toObjects(Quote::class.java),
                prevKey = null,
                nextKey = nextPage
            )

        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}