package com.nestpay.pg.data.di

import android.content.Context
import androidx.room.Room
import com.nestpay.pg.data.db.OrderDao
import com.nestpay.pg.data.db.OrderDatabase
import com.nestpay.pg.data.repository.local.datasource.OrderLocalDataSource
import com.nestpay.pg.data.repository.local.datasourceimpl.OrderLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    //room
    @Provides
    @Singleton
    fun provideOrderDatabase(
        @ApplicationContext context: Context
    ): OrderDatabase = Room.databaseBuilder(context, OrderDatabase::class.java, "database_order").build()


    /*@Provides
    @Singleton
    fun provideOrderDao(orderDatabase: OrderDatabase): OrderDao = orderDatabase.getOrderDao()*/

    @Provides
    @Singleton
    fun provideOrderLocalDataSource(database: OrderDatabase): OrderLocalDataSource {
        return OrderLocalDataSourceImpl(database)
    }

    /*@Provides
    @Singleton
    fun provideOrderTypeConverter(): OrderTypeConverter = OrderTypeConverter()*/

    //SharedPreferences 설정
    /*@Provides
    @Singleton
    fun provideOrderLocalDataSource(preferenceManager: PreferenceManager): OrderLocalDataSource {
        return LoginLocalDataSourceImpl(preferenceManager)
    }*/
}