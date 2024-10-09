package com.aarondeveloper.ticketing.di

import android.content.Context
import androidx.room.Room
import com.aarondeveloper.ticketing.data.local.database.TicketingDB
import com.aarondeveloper.ticketing.data.remote.api.TicketingApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    const val BASE_URL = "https://sistematicket.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideTicketingApi(moshi: Moshi): TicketingApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTicketingDB(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            TicketingDB::class.java,
            "TicketingDB.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providePrioridadDao(ticketingDB: TicketingDB) = ticketingDB.prioridadDao()

    @Provides
    fun provideTicketDao(ticketingDB: TicketingDB) = ticketingDB.ticketDao()
}