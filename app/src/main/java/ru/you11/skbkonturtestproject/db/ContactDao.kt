package ru.you11.skbkonturtestproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM Contact")
    fun getAllContacts(): List<DbContact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContacts(contact: List<DbContact>)
}