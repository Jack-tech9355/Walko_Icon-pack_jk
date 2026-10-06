package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.FavoriteIcon
import com.example.data.model.SavedCustomIcon

@Database(
    entities = [SavedCustomIcon::class, FavoriteIcon::class],
    version = 1,
    exportSchema = false
)
abstract class WalkoDatabase : RoomDatabase() {
    abstract fun walkoDao(): WalkoDao

    companion object {
        @Volatile
        private var INSTANCE: WalkoDatabase? = null

        fun getDatabase(context: Context): WalkoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WalkoDatabase::class.java,
                    "walko_icons_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
