package com.nestpay.pg.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.domain.model.local.Order
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(entities = [OrderEntity::class], version = 1)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun getOrderDao(): OrderDao

    /*companion object {
        fun getInstance(context: Context): OrderDatabase = Room
            .databaseBuilder(context, OrderDatabase::class.java, "database_order")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    Executors.newSingleThreadExecutor().execute {
                        runBlocking {
                            getInstance(context).getOrderDao().insertOrder(OrderEntity.DEFAULT_ORDER)
                        }
                    }
                }
            })
            .build()
    }*/
}