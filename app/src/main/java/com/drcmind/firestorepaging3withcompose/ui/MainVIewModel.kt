package com.drcmind.firestorepaging3withcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.drcmind.firestorepaging3withcompose.data.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVIewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {
    val quotes = repository.getQuotes().cachedIn(viewModelScope)
}