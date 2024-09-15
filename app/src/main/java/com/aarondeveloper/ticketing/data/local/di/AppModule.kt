package com.aarondeveloper.ticketing.data.local.di

import android.content.Context
import androidx.room.Room
import com.aarondeveloper.ticketing.data.local.database.TicketingDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
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