package ru.you11.skbkonturtestproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.you11.skbkonturtestproject.db.converters.DateConventer
import ru.you11.skbkonturtestproject.db.converters.TemperamentConventer

@Database(entities = [DbContact::class], version = 1)
@TypeConverters(DateConventer::class, TemperamentConventer::class)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "Contact_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}