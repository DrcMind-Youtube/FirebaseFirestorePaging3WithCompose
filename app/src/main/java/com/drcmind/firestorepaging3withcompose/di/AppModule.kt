package com.drcmind.firestorepaging3withcompose.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesQuotesQuery() = Firebase.firestore.collection("quotes")
        .orderBy("id")
        .limit(10L)
}