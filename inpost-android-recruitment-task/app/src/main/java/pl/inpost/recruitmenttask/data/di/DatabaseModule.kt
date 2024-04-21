package pl.inpost.recruitmenttask.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.localStorage.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.localStorage.db.ShipmentDatabase

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideShipmentNetworkItemDao(database: ShipmentDatabase): ShipmentDao {
        return database.shipmentNetworkItemDao
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ShipmentDatabase {
        return ShipmentDatabase(context)
    }
}